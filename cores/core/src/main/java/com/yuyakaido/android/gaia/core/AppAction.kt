package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class AppAction : ActionType<AppState> {
  sealed class CommunityAction : AppAction() {
    object ToLoading : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Loading
        )
      }
    }
    data class ToIdeal(val communities: List<Community.Detail>) : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Ideal(communities = communities)
        )
      }
    }
    object ToError : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Error
        )
      }
    }
  }
}