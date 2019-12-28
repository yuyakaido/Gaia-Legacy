package com.yuyakaido.android.gaia.core.domain.value

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.android.parcel.Parcelize

sealed class UserDetailPage : Parcelable {

  abstract suspend fun detail(
    repository: UserRepositoryType
  ): User.Detail

  @Parcelize
  object Me : UserDetailPage() {
    override suspend fun detail(
      repository: UserRepositoryType
    ): User.Detail {
      return repository.me()
    }
  }

  @Parcelize
  data class Other(
    val user: User
  ) : UserDetailPage() {
    override suspend fun detail(
      repository: UserRepositoryType
    ): User.Detail {
      return repository.detail(user = user)
    }
  }

}