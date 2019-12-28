package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

interface ArticleRepositoryType {

  suspend fun articlesOfCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesOfCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesOfUser(
    user: User,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun votedArticles(
    user: User,
    path: String
  ): EntityPaginationItem<Article>

  suspend fun vote(target: VoteTarget)

  suspend fun trendingArticles(): List<TrendingArticle>

  suspend fun search(query: String): EntityPaginationItem<Article>

}