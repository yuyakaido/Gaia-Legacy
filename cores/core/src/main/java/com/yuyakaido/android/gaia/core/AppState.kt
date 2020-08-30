package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Community

data class AppState(
  val community: CommunityState = CommunityState.Initial
) : StateType {

  sealed class CommunityState : StateType {
    object Initial : CommunityState()
    object Loading : CommunityState()
    object Error : CommunityState()
    data class Ideal(val communities: List<Community.Detail>) : CommunityState()
  }

}