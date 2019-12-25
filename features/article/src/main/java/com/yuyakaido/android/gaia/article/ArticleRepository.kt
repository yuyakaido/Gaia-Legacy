package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
  private val privateApi: PrivateApi,
  private val publicApi: PublicApi
) : ArticleRepositoryType {

  override suspend fun articles(
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return privateApi
      .articles(
        path = path,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun articles(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article> {
    return privateApi
      .articles(
        path = community.name,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun votedArticles(
    me: Me,
    path: String
  ): EntityPaginationItem<Article> {
    return privateApi
      .voted(
        user = me.name,
        type = path
      )
      .toArticlePaginationItem()
  }

  override suspend fun vote(target: VoteTarget) {
    privateApi.vote(id = target.article.name, dir = target.dir)
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