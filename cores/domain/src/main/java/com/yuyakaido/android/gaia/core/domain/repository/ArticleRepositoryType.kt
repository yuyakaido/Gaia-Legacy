package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

interface ArticleRepositoryType {

  suspend fun articles(
    path: String,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articles(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun votedArticles(
    me: Me,
    path: String
  ): EntityPaginationItem<Article>

  suspend fun vote(target: VoteTarget)

  suspend fun trendingArticles(): List<TrendingArticle>

  suspend fun search(query: String): EntityPaginationItem<Article>

}