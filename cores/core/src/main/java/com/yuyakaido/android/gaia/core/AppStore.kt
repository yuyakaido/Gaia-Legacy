package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStore(
  initialState: AppState = AppState()
) : StoreType<AppState, AppAction>(
  initialState = initialState
) {

  fun lifecycleAsFlow(): Flow<AppLifecycle> {
    return stateAsFlow().map { it.lifecycle }
  }

  fun articleAsFlow(): Flow<SessionState.ArticleState> {
    return stateAsFlow().map { it.session.article }
  }

  fun communityAsFlow(): Flow<SessionState.CommunityState> {
    return stateAsFlow().map { it.session.community }
  }

}