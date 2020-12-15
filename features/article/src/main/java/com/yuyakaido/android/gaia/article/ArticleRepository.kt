package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem

class ArticleRepository(
  private val api: ArticleApi
) : ArticleRepositoryType {

  override suspend fun articlesBySort(
    sort: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return api
      .articlesOfSort(
        sort = sort,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesByCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return api
      .articlesOfCommunity(
        community = path,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesByCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article> {
    return api
      .articlesOfCommunity(
        community = community.name,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesByUser(
    user: User,
    after: String?
  ): EntityPaginationItem<Article> {
    return api
      .articlesOfUser(
        user = user.name,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articlesByUser(
    user: User,
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return api
      .voted(
        user = user.name,
        type = path,
        after = after
      )
      .toArticlePaginationItem()
  }

}