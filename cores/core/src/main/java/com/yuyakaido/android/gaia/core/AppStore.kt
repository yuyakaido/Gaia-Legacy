package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class AppStore(
  initialState: AppState = AppState(),
  reducer: AppReducer = AppReducer
) : StoreType<AppState, AppAction, AppReducer>(
  initialState = initialState,
  reducer = reducer
) {

  fun communityAsFlow(): Flow<AppState.CommunityState> {
    return stateAsFlow().map { it.community }
  }

}