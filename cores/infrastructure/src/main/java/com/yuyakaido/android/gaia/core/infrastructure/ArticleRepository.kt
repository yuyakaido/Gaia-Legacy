package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.value.*
import javax.inject.Inject

@AppScope
class ArticleRepository @Inject constructor(
  private val publicService: RedditPublicService,
  private val authService: RedditAuthService
) {

  suspend fun articles(
    page: ArticleListPage,
    after: String?
  ): EntityPaginationItem<Article> {
    return authService
      .articles(
        path = page.path,
        after = after
      )
      .toArticlePaginationItem()
  }

  suspend fun votedArticles(
    me: Me,
    page: VoteListPage
  ): EntityPaginationItem<Article> {
    return authService
      .voted(
        user = me.name,
        type = page.path
      )
      .toArticlePaginationItem()
  }

  suspend fun vote(target: VoteTarget) {
    authService.vote(id = target.article.name, dir = target.dir)
  }

  suspend fun trendingArticles(): List<TrendingArticle> {
    return publicService
      .trending()
      .toEntities()
  }

  suspend fun search(query: String): EntityPaginationItem<Article> {
    return publicService
      .search(query = query)
      .toArticlePaginationItem()
  }

}