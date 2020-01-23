package com.yuyakaido.android.gaia.user

import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.local.AppDatabase
import com.yuyakaido.android.gaia.core.infrastructure.remote.PrivateApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
  private val api: PrivateApi,
  private val database: AppDatabase
) : UserRepositoryType {

  override fun detail(user: User): Flow<User.Detail> = flow {
    emit(api.user(user = user.name).toEntity())
  }

  override fun me(): Flow<User.Detail.Me> = flow {
    val meDao = database.meDao()
    meDao.findAll().firstOrNull()?.let { emit(it.toEntity()) }
    val schema = api.me().toSchema()
    meDao.insert(me = schema)
    emit(schema.toEntity())
  }

  override suspend fun moderators(
    community: Community.Summary
  ): List<User> {
    return api.moderators(community = community.name).toUsers()
  }

  override suspend fun contributors(
    community: Community.Summary
  ): List<User> {
    return emptyList()
  }

}