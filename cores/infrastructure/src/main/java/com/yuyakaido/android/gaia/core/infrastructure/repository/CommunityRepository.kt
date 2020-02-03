package com.yuyakaido.android.gaia.core.infrastructure.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.infrastructure.remote.PrivateApi

class CommunityRepository(
  private val api: PrivateApi
) : CommunityRepositoryType {

  override suspend fun mine(): EntityPaginationItem<Community.Detail> {
    return api.communitiesOfMine().toCommunityPaginationItem()
  }

  override suspend fun detail(community: Community.Summary): Community.Detail {
    return api.detail(community = community.name).toEntity()
  }

  override suspend fun subscribe(community: Community.Detail): Community.Detail {
    api.subscribe(name = community.name)
    return community.copy(isSubscriber = true)
  }

  override suspend fun unsubscribe(community: Community.Detail): Community.Detail {
    api.unsubscribe(name = community.name)
    return community.copy(isSubscriber = false)
  }

}