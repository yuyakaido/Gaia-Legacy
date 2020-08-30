package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

interface StateType

interface ActionType

interface ReducerType<S : StateType, A : ActionType> {
  fun reduce(state: S, action: A): S
}

@FlowPreview
@ExperimentalCoroutinesApi
abstract class StoreType<S : StateType, A : ActionType, R : ReducerType<S, A>>(
  private val initialState: S,
  private val reducer: R
) {

  private val state = ConflatedBroadcastChannel(initialState)

  fun stateAsValue(): S {
    return state.value
  }

  fun stateAsFlow(): Flow<S> {
    return state.asFlow()
  }

  fun dispatch(action: A) {
    val currentState = state.value
    val nextState = reducer.reduce(currentState, action)
    state.offer(nextState)
  }

  fun dispatch(
    scope: CoroutineScope,
    action: suspend (store: StoreType<S, A, R>) -> Unit,
    error: (store: StoreType<S, A, R>, t: Throwable) -> Unit = { _, _ -> Unit }
  ): Job {
    return scope.launch {
      try {
        action.invoke(this@StoreType)
      } catch (e: Exception) {
        Timber.e(e)
        error.invoke(this@StoreType, e)
      }
    }
  }

}
