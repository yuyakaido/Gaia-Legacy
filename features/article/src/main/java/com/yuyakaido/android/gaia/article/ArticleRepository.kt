package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.*
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
  private val privateApi: PrivateApi,
  private val publicApi: PublicApi
) : ArticleRepositoryType {

  override suspend fun articles(
    page: ArticleListPage,
    after: String?
  ): EntityPaginationItem<Article> {
    return privateApi
      .articles(
        path = page.path(),
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun votedArticles(
    me: Me,
    page: VoteListPage
  ): EntityPaginationItem<Article> {
    return privateApi
      .voted(
        user = me.name,
        type = page.path
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