package com.yuyakaido.android.gaia.core

object CommunityReducer {

  fun reduce(
    action: AppAction.CommunityAction,
    state: AppState.CommunityState
  ): AppState.CommunityState {
    return when (action) {
      is AppAction.CommunityAction.ToLoading -> {
        AppState.CommunityState.Loading
      }
      is AppAction.CommunityAction.ToIdeal -> {
        AppState.CommunityState.Ideal(
          communities = action.communities
        )
      }
    }
  }

}