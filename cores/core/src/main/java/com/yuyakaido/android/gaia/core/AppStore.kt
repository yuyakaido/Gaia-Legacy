package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStore(
  initialState: AppState = AppState()
) : StoreType<AppState, AppAction>(
  initialState = initialState
) {

  private fun sessionAsFlow(): Flow<SessionState> {
    return stateAsFlow().map { it.session }
  }

  fun lifecycleAsFlow(): Flow<AppLifecycle> {
    return stateAsFlow().map { it.lifecycle }
  }

  fun articleAsFlow(): Flow<SessionState.ArticleState> {
    return sessionAsFlow().map { it.article }
  }

  fun communityAsFlow(): Flow<SessionState.CommunityState> {
    return sessionAsFlow().map { it.community }
  }

}