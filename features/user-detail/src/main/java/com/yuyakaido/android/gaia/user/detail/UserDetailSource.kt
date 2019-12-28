package com.yuyakaido.android.gaia.user.detail

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.android.parcel.Parcelize

sealed class UserDetailSource : Parcelable {

  abstract fun page(): UserArticleListPage
  abstract suspend fun detail(
    repository: UserRepositoryType
  ): User.Detail

  @Parcelize
  object Me : UserDetailSource() {
    override fun page(): UserArticleListPage {
      return UserArticleListPage.Me
    }
    override suspend fun detail(
      repository: UserRepositoryType
    ): User.Detail {
      return repository.me()
    }
  }

  @Parcelize
  data class Other(
    val user: User
  ) : UserDetailSource() {
    override fun page(): UserArticleListPage {
      return UserArticleListPage.Other
    }
    override suspend fun detail(
      repository: UserRepositoryType
    ): User.Detail {
      return repository.detail(user = user)
    }
  }

}