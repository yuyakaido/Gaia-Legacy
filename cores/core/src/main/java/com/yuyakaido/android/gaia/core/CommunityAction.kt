package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.reduxkit.ActionType

sealed class CommunityAction : ActionType<AppState> {

  object ToInitial : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        community = state.community.toInitial()
      )
    }
  }

  data class ToLoading(
    val communities: List<Community.Detail>
  ) : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        community = state.community.toLoading(this)
      )
    }
  }

  data class ToIdeal(
    val communities: List<Community.Detail>,
    val after: String?
  ) : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        community = state.community.toIdeal(this)
      )
    }
  }

}
