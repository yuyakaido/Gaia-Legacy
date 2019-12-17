package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.*
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthApi
import com.yuyakaido.android.gaia.core.infrastructure.RedditPublicApi
import javax.inject.Inject

class ArticleRepository @Inject constructor(
  private val authApi: RedditAuthApi,
  private val publicApi: RedditPublicApi
) : ArticleRepositoryType {

  override suspend fun articles(
    page: ArticleListPage,
    after: String?
  ): EntityPaginationItem<Article> {
    return authApi
      .articles(
        path = page.path,
        after = after
      )
      .toArticlePaginationItem()
  }

  override suspend fun votedArticles(
    me: Me,
    page: VoteListPage
  ): EntityPaginationItem<Article> {
    return authApi
      .voted(
        user = me.name,
        type = page.path
      )
      .toArticlePaginationItem()
  }

  override suspend fun vote(target: VoteTarget) {
    authApi.vote(id = target.article.name, dir = target.dir)
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