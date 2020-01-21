package com.yuyakaido.android.storybook.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import dummy.DummyEntityPaginationItem.createDummyEntityPaginationItem
import dummy.DummyTrendingArticle
import javax.inject.Inject

class ArticleRepositoryStoryBook @Inject constructor() : ArticleRepositoryType {

  companion object {
    private const val SIZE = 10

  }

  override suspend fun articlesOfSort(sort: String, after: String?): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(size = SIZE, category = sort)
  }

  override suspend fun articlesOfCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(size = SIZE, category = path)
  }

  override suspend fun articlesOfCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(size = SIZE, category = community.name)
  }

  override suspend fun articlesOfUser(user: User, after: String?): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(size = SIZE, username = user.name)
  }

  override suspend fun articlesOfUser(
    user: User,
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(category = path, username = user.name)
  }

  override suspend fun vote(target: VoteTarget) = Unit

  override suspend fun trendingArticles(): List<TrendingArticle> {
    return DummyTrendingArticle.createList(10)
  }

  override suspend fun search(query: String): EntityPaginationItem<Article> {
    return createDummyEntityPaginationItem(size = SIZE)
  }
}