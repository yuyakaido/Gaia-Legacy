package com.yuyakaido.android.reduxkit

import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.asObservable
import kotlinx.coroutines.rx2.await
import timber.log.Timber

interface StateType

interface ActionType<S : StateType> {
  fun reduce(state: S): S
}

interface AsyncActionType<S : StateType> : ActionType<S> {
  override fun reduce(state: S): S = state
}

interface SuspendableActionType<S : StateType> : AsyncActionType<S> {
  suspend fun execute(selector: SelectorType<S>, dispatcher: DispatcherType<S>)
}

interface CompletableActionType<S : StateType> : AsyncActionType<S> {
  fun execute(selector: SelectorType<S>, dispatcher: DispatcherType<S>): Completable
}

interface SelectorType<S : StateType> {
  fun select(): S
}

interface DispatcherType<S : StateType> {
  fun dispatch(action: ActionType<S>)
}

abstract class MiddlewareType<S : StateType> {
  open suspend fun before(state: StateType, action: ActionType<S>) = Unit
  open suspend fun after(state: StateType, action: ActionType<S>) = Unit
}

class LoggerMiddleware<S : StateType> : MiddlewareType<S>() {
  override suspend fun before(state: StateType, action: ActionType<S>) {
    Timber.v("Before: action = $action")
  }
  override suspend fun after(state: StateType, action: ActionType<S>) {
    Timber.v("After: action = $action")
  }
}

class ThunkMiddlewareForCoroutine<S : StateType>(
  private val selector: SelectorType<S>,
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>() {
  override suspend fun before(state: StateType, action: ActionType<S>) {
    if (action is SuspendableActionType) {
      action.execute(selector, dispatcher)
    }
  }
}

class ThunkMiddlewareForReactive<S : StateType>(
  private val selector: SelectorType<S>,
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>() {
  override suspend fun before(state: StateType, action: ActionType<S>) {
    if (action is CompletableActionType<S>) {
      action.execute(selector, dispatcher).await()
    }
  }
}

abstract class StoreType<S : StateType, A : ActionType<S>>(
  initialState: S,
  private val errorHandler: (e: Exception) -> Unit
) : SelectorType<S>, DispatcherType<S> {

  private val state = MutableStateFlow(initialState)
  private val middlewares by lazy {
    mutableListOf(
      LoggerMiddleware(),
      ThunkMiddlewareForCoroutine(selector = this, dispatcher = this),
      ThunkMiddlewareForReactive(selector = this, dispatcher = this)
    )
  }

  private fun update(action: ActionType<S>) {
    val currentState = stateAsValue()
    val nextState = action.reduce(currentState)
    state.value = nextState
  }

  override fun select(): S {
    return stateAsValue()
  }

  override fun dispatch(action: ActionType<S>) {
    update(action)
  }

  fun dispatch(
    scope: CoroutineScope,
    action: ActionType<S>
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

  fun stateAsObservable(): Observable<S> {
    return stateAsFlow().asObservable()
  }

}
