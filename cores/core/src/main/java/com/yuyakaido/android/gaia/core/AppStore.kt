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

  fun articleAsFlow(): Flow<AppState.ArticleState> {
    return stateAsFlow().map { it.article }
  }

  fun communityAsFlow(): Flow<AppState.CommunityState> {
    return stateAsFlow().map { it.community }
  }

}