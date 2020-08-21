package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class AppAction {
  sealed class CommunityAction : AppAction() {
    object ToLoading : CommunityAction()
    data class ToIdeal(val communities: List<Community.Detail>) : CommunityAction()
  }
}