package com.yuyakaido.android.reduxkit

import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.rx2.asObservable
import kotlinx.coroutines.rx2.await
import timber.log.Timber

interface StateType

interface ActionType<S : StateType> {
  fun reduce(state: S): S
}

interface SuspendableActionType<S : StateType> : ActionType<S> {
  override fun reduce(state: S): S = state
  suspend fun execute(selector: SelectorType<S>, dispatcher: DispatcherType<S>): ActionType<S>
}

interface SingleActionType<S : StateType> : ActionType<S> {
  override fun reduce(state: S): S = state
  fun execute(selector: SelectorType<S>, dispatcher: DispatcherType<S>): Single<out ActionType<S>>
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

class ThunkMiddlewareForCoroutine<S : StateType>(
  private val selector: SelectorType<S>,
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType<S>): ActionType<S> {
    if (action is SuspendableActionType) {
      return action.execute(selector, dispatcher)
    }
    return action
  }
}

class ThunkMiddlewareForReactive<S : StateType>(
  private val selector: SelectorType<S>,
  private val dispatcher: DispatcherType<S>
) : MiddlewareType<S>(dispatcher) {
  override suspend fun before(state: StateType, action: ActionType<S>): ActionType<S> {
    if (action is SingleActionType) {
      return action.execute(selector, dispatcher).await()
    }
    return action
  }
}

abstract class StoreType<S : StateType, A : ActionType<S>>(
  initialState: S,
  private val errorHandler: (e: Exception) -> Unit
) : SelectorType<S>, DispatcherType<S> {

  private val state = ConflatedBroadcastChannel(initialState)
  private val middlewares by lazy {
    mutableListOf(
      LoggerMiddleware(dispatcher = this),
      ThunkMiddlewareForCoroutine(selector = this, dispatcher = this),
      ThunkMiddlewareForReactive(selector = this, dispatcher = this)
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
      try {
        val actualAction = middlewares.fold(action) { action, middleware ->
          return@fold middleware.before(
            state = stateAsValue(),
            action = action
          )
        }
        update(actualAction)
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
    return state.asFlow()
  }

  fun stateAsObservable(): Observable<S> {
    return stateAsFlow().asObservable()
  }

}
