package com.yuyakaido.android.gaia.core

object CommunityReducer : ReducerType<AppState.CommunityState, AppAction.CommunityAction> {

  override fun reduce(
    state: AppState.CommunityState,
    action: AppAction.CommunityAction
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
      is AppAction.CommunityAction.ToError -> {
        AppState.CommunityState.Error
      }
    }
  }

}