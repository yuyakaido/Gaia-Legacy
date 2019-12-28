package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community

interface CommunityRepositoryType {
  suspend fun detail(community: Community.Summary): Community.Detail
  suspend fun subscribe(community: Community.Detail): Community.Detail
  suspend fun unsubscribe(community: Community.Detail): Community.Detail
}