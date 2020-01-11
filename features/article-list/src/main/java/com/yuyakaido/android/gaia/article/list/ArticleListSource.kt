package com.yuyakaido.android.gaia.article.list

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import kotlinx.android.parcel.Parcelize

sealed class ArticleListSource : Parcelable {

  abstract suspend fun articles(
    repository: ArticleRepositoryType,
    after: String?
  ): EntityPaginationItem<Article>

  @Parcelize
  object Popular : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfCommunity(
          path = "popular",
          after = after
        )
    }
  }

  @Parcelize
  object Best : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "best",
          after = after
        )
    }
  }

  @Parcelize
  object Hot : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "hot",
          after = after
        )
    }
  }

  @Parcelize
  object New : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "new",
          after = after
        )
    }
  }

  @Parcelize
  object Top : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "top",
          after = after
        )
    }
  }

  @Parcelize
  object Controversial : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "controversial",
          after = after
        )
    }
  }

  @Parcelize
  object Rising : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "rising",
          after = after
        )
    }
  }

  @Parcelize
  object Random : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfSort(
          sort = "random",
          after = after
        )
    }
  }

  @Parcelize
  data class CommunityDetail(
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
  data class Upvote(
    val user: User
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfUser(
          user = user,
          path = "upvoted",
          after = after
        )
    }
  }

  @Parcelize
  data class Downvote(
    val user: User
  ) : ArticleListSource() {
    override suspend fun articles(
      repository: ArticleRepositoryType,
      after: String?
    ): EntityPaginationItem<Article> {
      return repository
        .articlesOfUser(
          user = user,
          path = "downvoted",
          after = after
        )
    }
  }

}