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
  suspend fun execute(dispatcher: DispatcherType)
}

interface ReducerType<S : StateType, A : ActionType> {
  fun reduce(state: S, action: A): S
}

interface DispatcherType {
  fun dispatch(action: ActionType)
}

abstract class Middleware<S : StateType, A : ActionType>(
  dispatcher: DispatcherType
) {
  open suspend fun before(state: S, action: A) = Unit
  open suspend fun after(state: S, action: A) = Unit
}

@FlowPreview
@ExperimentalCoroutinesApi
class LoggerMiddleware<S : StateType, A : ActionType>(
  private val dispatcher: DispatcherType
) : Middleware<S, A>(dispatcher) {
  override suspend fun before(state: S, action: A) {
    Timber.v("Before: action = $action")
  }
  override suspend fun after(state: S, action: A) {
    Timber.v("After: action = $action")
  }
}

@FlowPreview
@ExperimentalCoroutinesApi
class ThunkMiddleware<S : StateType, A : ActionType>(
  private val dispatcher: DispatcherType
) : Middleware<S, A>(dispatcher) {
  override suspend fun before(state: S, action: A) {
    if (action is AsyncActionType) {
      action.execute(dispatcher)
    }
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
      LoggerMiddleware<S, A>(this),
      ThunkMiddleware<S, A>(this)
    )
  }

  private fun update(action: A) {
    val currentState = stateAsValue()
    val nextState = reducer.reduce(currentState, action)
    state.offer(nextState)
  }

  override fun dispatch(action: ActionType) {
    update(action as A)
  }

  fun dispatch(
    scope: CoroutineScope,
    action: AsyncActionType
  ): Job {
    return scope.launch {
      middlewares.forEach { middleware ->
        middleware.before(
          state = stateAsValue(),
          action = action as A
        )
      }
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
