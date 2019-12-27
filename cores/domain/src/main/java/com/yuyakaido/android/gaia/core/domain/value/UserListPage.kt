package com.yuyakaido.android.gaia.core.domain.value

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.android.parcel.Parcelize

sealed class UserListPage : Parcelable {

  abstract suspend fun users(
    repository: UserRepositoryType
  ): List<User>

  @Parcelize
  data class Moderator(
    val community: Community.Summary
  ) : UserListPage() {
    override suspend fun users(
      repository: UserRepositoryType
    ): List<User> {
      return repository.moderators(community = community)
    }
  }

  @Parcelize
  data class Contributor(
    val community: Community.Summary
  ) : UserListPage() {
    override suspend fun users(
      repository: UserRepositoryType
    ): List<User> {
      return repository.contributors(community = community)
    }
  }

}