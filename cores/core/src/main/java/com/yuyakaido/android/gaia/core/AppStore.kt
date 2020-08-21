package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
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

  fun communityAsFlow(): Flow<AppState.CommunityState> {
    return flow().map { it.community }
  }

}