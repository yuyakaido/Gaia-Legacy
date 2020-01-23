package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryType {
  fun detail(user: User): Flow<User.Detail>
  fun me(): Flow<User.Detail.Me>
  suspend fun moderators(community: Community.Summary): List<User>
  suspend fun contributors(community: Community.Summary): List<User>
}