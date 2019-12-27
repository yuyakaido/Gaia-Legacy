package com.yuyakaido.android.gaia.user

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val api: PrivateApi
) : UserRepositoryType {

  override suspend fun moderators(
    community: Community.Summary
  ): List<User> {
    return api.moderators(name = community.name).toUsers()
  }

  override suspend fun contributors(
    community: Community.Summary
  ): List<User> {
//    return api.contributors(name = community.name).toUsers()
    return emptyList()
  }

}