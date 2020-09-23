package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem

interface CommunityRepositoryType {
  suspend fun mine(
    after: String?
  ): EntityPaginationItem<Community.Detail>
  suspend fun detail(community: Community.Summary): Community.Detail
  suspend fun subscribe(community: Community.Detail): Community.Detail
  suspend fun unsubscribe(community: Community.Detail): Community.Detail
}