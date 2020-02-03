package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.remote.api.PublicApi

class ArticleRepository(
  private val publicApi: PublicApi,
  private val articleApi: ArticleApi
) : ArticleRepositoryType {

  override suspend fun articlesOfSort(
    sort: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return articleApi
      .articlesOfSort(
        sort = sort,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesOfCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return articleApi
      .articlesOfCommunity(
        community = path,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesOfCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article> {
    return articleApi
      .articlesOfCommunity(
        community = community.name,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesOfUser(
    user: User,
    after: String?
  ): EntityPaginationItem<Article> {
    return articleApi
      .articlesOfUser(
        user = user.name,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesOfUser(
    user: User,
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return articleApi
      .voted(
        user = user.name,
        type = path,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun vote(target: VoteTarget) {
    articleApi.vote(id = target.entity.name, dir = target.dir)
  }

  override suspend fun trendingArticles(): List<TrendingArticle> {
    return publicApi
      .trending()
      .toEntities()
  }

  override suspend fun search(query: String): EntityPaginationItem<Article> {
    return publicApi
      .search(query = query)
      .toArticlePaginationItem()
  }

}