package com.yuyakaido.android.gaia.core

object AppReducer {

  fun reduce(action: AppAction, state: AppState): AppState {
    return when (action) {
      is AppAction.CommunityAction -> {
        state.copy(
          community = CommunityReducer.reduce(action, state.community)
        )
      }
    }
  }

}