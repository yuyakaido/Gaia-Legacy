package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class AppStore(
  initialState: AppState = AppState()
) : StoreType<AppState, AppAction>(
  initialState = initialState
) {

  fun articleAsFlow(): Flow<AppState.ArticleState> {
    return stateAsFlow().map { it.article }
  }

  fun communityAsFlow(): Flow<AppState.CommunityState> {
    return stateAsFlow().map { it.community }
  }

}