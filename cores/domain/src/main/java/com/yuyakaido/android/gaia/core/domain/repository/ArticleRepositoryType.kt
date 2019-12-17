package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.value.*

interface ArticleRepositoryType {

  suspend fun articles(
    page: ArticleListPage,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun votedArticles(
    me: Me,
    page: VoteListPage
  ): EntityPaginationItem<Article>

  suspend fun vote(target: VoteTarget)

  suspend fun trendingArticles(): List<TrendingArticle>

  suspend fun search(query: String): EntityPaginationItem<Article>

}