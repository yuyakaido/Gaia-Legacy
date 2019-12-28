package com.yuyakaido.android.gaia.core.domain.value

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import kotlinx.android.parcel.Parcelize

sealed class ArticleListPage : Parcelable {

  abstract suspend fun articles(
    repository: ArticleRepositoryType,
    after: String?
  ): EntityPaginationItem<Article>

  @Parcelize
  object Popular : ArticleListPage(), Parcelable {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articles(
          path = "popular",
          after = after
        )
    }
  }

  @Parcelize
  data class CommunityDetail(
    val community: Community.Summary
  ) : ArticleListPage(), Parcelable {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articles(
          community = community,
          after = after
        )
    }
  }

  @Parcelize
  data class Upvote(
    val user: User
  ) : ArticleListPage(), Parcelable {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .votedArticles(
          user = user,
          path = "upvoted"
        )
    }
  }

  @Parcelize
  data class Downvote(
    val user: User
  ) : ArticleListPage(), Parcelable {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .votedArticles(
          user = user,
          path = "downvoted"
        )
    }
  }

}