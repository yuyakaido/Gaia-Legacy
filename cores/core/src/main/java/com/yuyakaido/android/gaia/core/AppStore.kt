package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class AppStore @Inject constructor() {

  private val channel = ConflatedBroadcastChannel(AppState())

  private fun flow(): Flow<AppState> {
    return channel.asFlow()
  }

  fun dispatch(action: AppAction) = synchronized(this) {
    val currentState = channel.value
    val nextState = AppReducer.reduce(action, currentState)
    channel.offer(nextState)
  }

  fun execute(
    scope: CoroutineScope,
    action: suspend (store: AppStore) -> Unit,
    error: (store: AppStore, t: Throwable) -> Unit = { _, _ -> Unit }
  ): Job {
    return scope.launch {
      try {
        action.invoke(this@AppStore)
      } catch (e: Exception) {
        Timber.e(e)
        error.invoke(this@AppStore, e)
      }
    }
  }

  fun communityAsFlow(): Flow<AppState.CommunityState> {
    return flow().map { it.community }
  }

}