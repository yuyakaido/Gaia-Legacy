package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User

interface UserRepositoryType {
  suspend fun moderators(community: Community.Summary): List<User>
  suspend fun contributors(community: Community.Summary): List<User>
}