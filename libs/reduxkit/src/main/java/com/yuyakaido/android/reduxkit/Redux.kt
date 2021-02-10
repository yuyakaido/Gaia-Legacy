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

interface ActionType<ROOT : StateType, SCOPE : StateType> {
  fun reduce(state: ROOT): ROOT
}

interface AsyncActionType<ROOT : StateType, SCOPE : StateType> : ActionType<ROOT, SCOPE> {
  fun selector(root: SelectorType<ROOT>): SelectorType<SCOPE>
  override fun reduce(state: ROOT): ROOT = state
}

interface SuspendableActionType<ROOT : StateType, SCOPE : StateType> : AsyncActionType<ROOT, SCOPE> {
  suspend fun execute(selector: SelectorType<SCOPE>, dispatcher: DispatcherType<ROOT>)
}

interface CompletableActionType<ROOT : StateType, SCOPE : StateType> : AsyncActionType<ROOT, SCOPE> {
  fun execute(selector: SelectorType<SCOPE>, dispatcher: DispatcherType<ROOT>): Completable
}

interface SelectorType<out S : StateType> {
  fun select(): S
}

interface DispatcherType<ROOT : StateType> {
  fun <SCOPE : StateType> dispatch(action: ActionType<ROOT, SCOPE>)
}

abstract class MiddlewareType<ROOT : StateType> {
  open suspend fun <SCOPE : StateType> before(state: StateType, action: ActionType<ROOT, SCOPE>) = Unit
  open suspend fun <SCOPE : StateType> after(state: StateType, action: ActionType<ROOT, SCOPE>) = Unit
}

class LoggerMiddleware<ROOT : StateType> : MiddlewareType<ROOT>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: ActionType<ROOT, SCOPE>) {
    Timber.v("Before: action = $action")
  }
  override suspend fun <SCOPE : StateType> after(state: StateType, action: ActionType<ROOT, SCOPE>) {
    Timber.v("After: action = $action")
  }
}

class ThunkMiddlewareForCoroutine<ROOT : StateType>(
  private val selector: SelectorType<ROOT>,
  private val dispatcher: DispatcherType<ROOT>
) : MiddlewareType<ROOT>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: ActionType<ROOT, SCOPE>) {
    if (action is SuspendableActionType) {
      action.execute(action.selector(selector), dispatcher)
    }
  }
}

class ThunkMiddlewareForReactive<ROOT : StateType>(
  private val selector: SelectorType<ROOT>,
  private val dispatcher: DispatcherType<ROOT>
) : MiddlewareType<ROOT>() {
  override suspend fun <SCOPE : StateType> before(state: StateType, action: ActionType<ROOT, SCOPE>) {
    if (action is CompletableActionType) {
      action.execute(action.selector(selector), dispatcher).await()
    }
  }
}

abstract class StoreType<ROOT : StateType, A : ActionType<ROOT, *>>(
  initialState: ROOT,
  private val errorHandler: (e: Exception) -> Unit
) : SelectorType<ROOT>, DispatcherType<ROOT> {

  private val state = MutableStateFlow(initialState)
  private val middlewares by lazy {
    mutableListOf(
      LoggerMiddleware(),
      ThunkMiddlewareForCoroutine(selector = this, dispatcher = this),
      ThunkMiddlewareForReactive(selector = this, dispatcher = this)
    )
  }

  private fun <SCOPE : StateType> update(action: ActionType<ROOT, SCOPE>) {
    val currentState = stateAsValue()
    val nextState = action.reduce(currentState)
    state.value = nextState
  }

  override fun select(): ROOT {
    return stateAsValue()
  }

  override fun <SCOPE : StateType> dispatch(action: ActionType<ROOT, SCOPE>) {
    update(action)
  }

  fun <SCOPE : StateType> dispatch(
    scope: CoroutineScope,
    action: ActionType<ROOT, SCOPE>
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

  fun addMiddleware(middleware: MiddlewareType<ROOT>) {
    middlewares.add(middleware)
  }

  fun removeMiddleware(middleware: MiddlewareType<ROOT>) {
    middlewares.remove(middleware)
  }

  fun stateAsValue(): ROOT {
    return state.value
  }

  fun stateAsFlow(): Flow<ROOT> {
    return state
  }

}
