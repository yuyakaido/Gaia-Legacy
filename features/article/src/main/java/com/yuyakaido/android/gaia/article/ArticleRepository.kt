package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.*
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import com.yuyakaido.android.gaia.core.infrastructure.RedditPublicService
import javax.inject.Inject

class ArticleRepository @Inject constructor(
  private val authService: RedditAuthService,
  private val publicService: RedditPublicService
) : ArticleRepositoryType {

  override suspend fun articles(
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

  override suspend fun votedArticles(
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

  override suspend fun vote(target: VoteTarget) {
    authService.vote(id = target.article.name, dir = target.dir)
  }

  override suspend fun trendingArticles(): List<TrendingArticle> {
    return publicService
      .trending()
      .toEntities()
  }

  override suspend fun search(query: String): EntityPaginationItem<Article> {
    return publicService
      .search(query = query)
      .toArticlePaginationItem()
  }

}