package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

interface StateType

interface ActionType

@FlowPreview
@ExperimentalCoroutinesApi
interface AsyncActionType : ActionType {
  suspend fun execute(dispatcher: DispatcherType): ActionType
}

interface ReducerType<S : StateType, A : ActionType> {
  fun reduce(state: S, action: A): S
}

interface DispatcherType {
  fun dispatch(action: ActionType)
}

abstract class Middleware(
  dispatcher: DispatcherType
) {
  open suspend fun before(state: StateType, action: ActionType): ActionType = action
  open suspend fun after(state: StateType, action: ActionType): ActionType = action
}

@FlowPreview
@ExperimentalCoroutinesApi
class LoggerMiddleware(
  dispatcher: DispatcherType
) : Middleware(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType): ActionType {
    Timber.v("Before: action = $action")
    return action
  }
  override suspend fun after(state: StateType, action: ActionType): ActionType {
    Timber.v("After: action = $action")
    return action
  }
}

@FlowPreview
@ExperimentalCoroutinesApi
class ThunkMiddleware(
  private val dispatcher: DispatcherType
) : Middleware(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType): ActionType {
    if (action is AsyncActionType) {
      return action.execute(dispatcher)
    }
    return action
  }
}

@FlowPreview
@ExperimentalCoroutinesApi
abstract class StoreType<S : StateType, A : ActionType, R : ReducerType<S, A>>(
  private val initialState: S,
  private val reducer: R
) : DispatcherType {

  private val state = ConflatedBroadcastChannel(initialState)
  private val middlewares by lazy {
    listOf(
      LoggerMiddleware(this),
      ThunkMiddleware(this)
    )
  }

  private fun update(action: ActionType) {
    val currentState = stateAsValue()
    val nextState = reducer.reduce(currentState, action as A)
    state.offer(nextState)
  }

  override fun dispatch(action: ActionType) {
    update(action)
  }

  fun dispatch(
    scope: CoroutineScope,
    action: ActionType
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

  fun stateAsValue(): S {
    return state.value
  }

  fun stateAsFlow(): Flow<S> {
    return state.asFlow()
  }

}
