package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

class ArticleRepository(
  private val api: ArticleApi
) : ArticleRepositoryType {

  override suspend fun articlesOfSort(
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

  override suspend fun articlesOfCommunity(
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

  override suspend fun articlesOfCommunity(
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

  override suspend fun articlesOfUser(
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

  override suspend fun articlesOfUser(
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

  override suspend fun vote(target: VoteTarget) {
    api.vote(id = target.entity.name, dir = target.dir)
  }

}