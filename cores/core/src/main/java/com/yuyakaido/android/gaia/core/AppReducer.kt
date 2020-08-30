package com.yuyakaido.android.gaia.core

object AppReducer : ReducerType<AppState, AppAction> {

  override fun reduce(
    state: AppState,
    action: AppAction
  ): AppState {
    return when (action) {
      is AppAction.CommunityAction -> {
        state.copy(
          community = CommunityReducer.reduce(
            state = state.community,
            action = action
          )
        )
      }
    }
  }

}