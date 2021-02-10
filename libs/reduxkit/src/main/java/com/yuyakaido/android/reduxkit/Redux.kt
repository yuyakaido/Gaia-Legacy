package com.yuyakaido.android.reduxkit

import io.reactivex.Completable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import timber.log.Timber

interface StateType

interface NewActionType<ROOT : StateType, SCOPE : StateType> {
  fun reduce(state: ROOT): ROOT
}

interface NewAsyncActionType<ROOT : StateType, SCOPE : StateType> : NewActionType<ROOT, SCOPE> {
  fun selector(): SelectorType<SCOPE>
  override fun reduce(state: ROOT): ROOT = state
}

interface NewSuspendableActionType<ROOT : StateType, SCOPE : StateType> : NewAsyncActionType<ROOT, SCOPE> {
  suspend fun execute(selector: SelectorType<SCOPE>, dispatcher: DispatcherType<ROOT>)
}

interface NewCompletableActionType<ROOT : StateType, SCOPE : StateType> : NewAsyncActionType<ROOT, SCOPE> {
  fun execute(selector: SelectorType<SCOPE>, dispatcher: DispatcherType<ROOT>): Completable
}

interface SelectorType<out S : StateType> {
  fun select(): S
}

interface DispatcherType<S : StateType> {
  fun <SCOPE : StateType> dispatch(action: NewActionType<S, SCOPE>)
}

abstract class MiddlewareType<S : StateType> {
  open suspend fun <SCOPE : StateType> before(state: StateType, action: NewActionType<S, SCOPE>) = Unit
  open suspend fun <SCOPE : StateType> after(state: StateType, action: NewActionType<S, SCOPE>) = Unit
}

class LoggerMiddleware<S : StateType> : MiddlewareType<S>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: NewActionType<S, SCOPE>) {
    Timber.v("Before: action = $action")
  }
  override suspend fun <SCOPE : StateType> after(state: StateType, action: NewActionType<S, SCOPE>) {
    Timber.v("After: action = $action")
  }
}

class ThunkMiddlewareForCoroutine<S : StateType>(
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: NewActionType<S, SCOPE>) {
    if (action is NewSuspendableActionType) {
      action.execute(action.selector(), dispatcher)
    }
  }
}

class ThunkMiddlewareForReactive<S : StateType>(
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: NewActionType<S, SCOPE>) {
    if (action is NewCompletableActionType) {
      action.execute(action.selector(), dispatcher).await()
    }
  }
}

abstract class StoreType<S : StateType, A : NewActionType<S, *>>(
  initialState: S,
  private val errorHandler: (e: Exception) -> Unit
) : DispatcherType<S> {

  private val state = MutableStateFlow(initialState)
  private val middlewares by lazy {
    mutableListOf(
      LoggerMiddleware(),
      ThunkMiddlewareForCoroutine(dispatcher = this),
      ThunkMiddlewareForReactive(dispatcher = this)
    )
  }

  private fun <SCOPE : StateType> update(action: NewActionType<S, SCOPE>) {
    val currentState = stateAsValue()
    val nextState = action.reduce(currentState)
    state.value = nextState
  }

  override fun <SCOPE : StateType> dispatch(action: NewActionType<S, SCOPE>) {
    update(action)
  }

  fun <SCOPE : StateType> dispatch(
    scope: CoroutineScope,
    action: NewActionType<S, SCOPE>
  ): Job {
    return scope.launch {
      try {
        middlewares.forEach { middleware ->
          middleware.before(
            state = stateAsValue(),
            action = action
          )
        }
        update(action)
        middlewares.forEach { middleware ->
          middleware.after(
            state = stateAsValue(),
            action = action
          )
        }
      } catch (e: Exception) {
        errorHandler.invoke(e)
      }
    }
  }

  fun addMiddleware(middleware: MiddlewareType<S>) {
    middlewares.add(middleware)
  }

  fun removeMiddleware(middleware: MiddlewareType<S>) {
    middlewares.remove(middleware)
  }

  fun stateAsValue(): S {
    return state.value
  }

  fun stateAsFlow(): Flow<S> {
    return state
  }

}
