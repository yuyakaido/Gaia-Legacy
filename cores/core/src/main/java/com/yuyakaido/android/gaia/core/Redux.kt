package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

interface StateType

interface ActionType<S : StateType> {
  fun reduce(state: S): S
}

interface AsyncActionType<S : StateType> : ActionType<S> {
  override fun reduce(state: S): S = state
  suspend fun execute(selector: SelectorType<S>, dispatcher: DispatcherType<S>): ActionType<S>
}

interface SelectorType<S : StateType> {
  fun select(): S
}

interface DispatcherType<S : StateType> {
  fun dispatch(action: ActionType<S>)
}

abstract class MiddlewareType<S : StateType>(
  dispatcher: DispatcherType<S>
) {
  open suspend fun before(state: StateType, action: ActionType<S>): ActionType<S> = action
  open suspend fun after(state: StateType, action: ActionType<S>): ActionType<S> = action
}

class LoggerMiddleware<S : StateType>(
  dispatcher: DispatcherType<S>
) : MiddlewareType<S>(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType<S>): ActionType<S> {
    Timber.v("Before: action = $action")
    return action
  }
  override suspend fun after(state: StateType, action: ActionType<S>): ActionType<S> {
    Timber.v("After: action = $action")
    return action
  }
}

class ThunkMiddleware<S : StateType>(
  private val selector: SelectorType<S>,
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType<S>): ActionType<S> {
    if (action is AsyncActionType) {
      return action.execute(selector, dispatcher)
    }
    return action
  }
}

@FlowPreview
@ExperimentalCoroutinesApi
abstract class StoreType<S : StateType, A : ActionType<S>>(
  initialState: S
) : SelectorType<S>, DispatcherType<S> {

  private val state = ConflatedBroadcastChannel(initialState)
  private val middlewares by lazy {
    mutableListOf(
      LoggerMiddleware(dispatcher = this),
      ThunkMiddleware(dispatcher = this, selector = this)
    )
  }

  private fun update(action: ActionType<S>) {
    val currentState = stateAsValue()
    val nextState = action.reduce(currentState)
    state.offer(nextState)
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
      val actualAction = middlewares.fold(action) { action, middleware ->
        return@fold middleware.before(
          state = stateAsValue(),
          action = action as A
        )
      }
      update(actualAction)
      middlewares.forEach { middleware ->
        middleware.after(
          state = stateAsValue(),
          action = action as A
        )
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
    return state.asFlow()
  }

}
