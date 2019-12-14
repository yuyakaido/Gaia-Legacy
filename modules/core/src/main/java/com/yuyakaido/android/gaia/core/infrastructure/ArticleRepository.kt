package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.app.AppScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.entity.Me
import com.yuyakaido.android.gaia.core.value.ArticleListPage
import com.yuyakaido.android.gaia.core.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.value.VoteListPage
import com.yuyakaido.android.gaia.core.value.VoteResult
import javax.inject.Inject

@AppScope
class ArticleRepository @Inject constructor(
  private val service: RedditAuthService
) {

  suspend fun articles(
    page: ArticleListPage,
    after: String?
  ): EntityPaginationItem<Article> {
    return service
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
    return service
      .voted(
        user = me.name,
        type = page.path
      )
      .toArticlePaginationItem()
  }

  suspend fun vote(result: VoteResult) {
    service.vote(id = result.article.name, dir = result.dir)
  }

}