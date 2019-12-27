package com.yuyakaido.android.gaia.user

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val api: PrivateApi
) {

  suspend fun moderators(community: Community.Summary): List<User> {
    return api.moderators(name = community.name).toUsers()
  }

}