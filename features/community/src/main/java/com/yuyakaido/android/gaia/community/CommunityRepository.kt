package com.yuyakaido.android.gaia.community

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import javax.inject.Inject

class CommunityRepository @Inject constructor(
  private val api: PrivateApi
) {

  suspend fun detail(community: Community.Summary): Community.Detail {
    return api.detail(community = community.name).toEntity()
  }

  suspend fun subscribe(community: Community.Detail): Community.Detail {
    api.subscribe(name = community.name)
    return community.copy(isSubscriber = true)
  }

  suspend fun unsubscribe(community: Community.Detail): Community.Detail {
    api.unsubscribe(name = community.name)
    return community.copy(isSubscriber = false)
  }

}