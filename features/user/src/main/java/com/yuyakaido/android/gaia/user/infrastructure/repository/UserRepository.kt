package com.yuyakaido.android.gaia.user.infrastructure.repository

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.user.infrastructure.local.MeDatabase
import com.yuyakaido.android.gaia.user.infrastructure.remote.UserApi

class UserRepository(
  private val api: UserApi,
  private val database: MeDatabase
) : UserRepositoryType {

  override suspend fun detail(user: User): User.Detail {
    return api.user(user = user.name).toEntity()
  }

  override suspend fun me(): User.Detail.Me {
    val schema = api.me().toSchema()
    database.meDao().insert(me = schema)
    return schema.toEntity()
  }

  override suspend fun moderators(
    community: Community.Summary
  ): List<User> {
    return api.moderators(community = community.name.value).toUsers()
  }

  override suspend fun contributors(
    community: Community.Summary
  ): List<User> {
    return emptyList()
  }

}