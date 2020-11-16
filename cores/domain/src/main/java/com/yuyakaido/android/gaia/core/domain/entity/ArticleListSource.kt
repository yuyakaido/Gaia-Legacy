package com.yuyakaido.android.gaia.core.domain.entity

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import kotlinx.android.parcel.Parcelize

sealed class ArticleListSource : Parcelable {

  abstract val id: String

  abstract suspend fun articles(
    repository: ArticleRepositoryType,
    after: String?
  ): EntityPaginationItem<Article>

  @Parcelize
  data class Popular(
    override val id: String = "popular"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfCommunity(
          path = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Best(
    override val id: String = "best"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Hot(
    override val id: String = "hot"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class New(
    override val id: String = "new"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Top(
    override val id: String = "top"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Controversial(
    override val id: String = "controversial"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Rising(
    override val id: String = "rising"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Random(
    override val id: String = "random"
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = id,
          after = after
        )
    }
  }

  @Parcelize
  data class CommunityDetail(
    override val id: String = "community_detail",
    val community: Community.Summary
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfCommunity(
          community = community,
          after = after
        )
    }
  }

  @Parcelize
  data class Submit(
    override val id: String = "submit",
    val user: User
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfUser(
          user = user,
          after = after
        )
    }
  }

  @Parcelize
  data class Upvoted(
    override val id: String = "upvoted",
    val user: User
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfUser(
          user = user,
          path = id,
          after = after
        )
    }
  }

  @Parcelize
  data class Downvoted(
    override val id: String = "downvoted",
    val user: User
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfUser(
          user = user,
          path = id,
          after = after
        )
    }
  }

}