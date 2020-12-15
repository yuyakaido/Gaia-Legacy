package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem

interface ArticleRepositoryType {

  suspend fun articlesBySort(
    sort: String,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesByCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesByCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesByUser(
    user: User,
    after: String?
  ): EntityPaginationItem<Article>

  suspend fun articlesByUser(
    user: User,
    path: String,
    after: String?
  ): EntityPaginationItem<Article>

}