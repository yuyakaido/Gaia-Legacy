package com.yuyakaido.android.storybooks.article.list

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import timber.log.Timber
import javax.inject.Inject

class ArticleRepositoryStoryBook @Inject constructor() : ArticleRepositoryType {

  private val dummyEntityPaginationItem =
    EntityPaginationItem((0 until 20).map { id -> Article.create(id = id.toString()) }, "", "").apply {
      entities.forEach{ Timber.d("${it.id}")}
    }

  private val trendingArticles = List(10) { TrendingArticle(name = "trend") }

  override suspend fun articlesOfSort(sort: String, after: String?): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }

  override suspend fun articlesOfCommunity(
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }

  override suspend fun articlesOfCommunity(
    community: Community.Summary,
    after: String?
  ): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }

  override suspend fun articlesOfUser(user: User, after: String?): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }

  override suspend fun articlesOfUser(
    user: User,
    path: String,
    after: String?
  ): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }

  override suspend fun vote(target: VoteTarget) = Unit

  override suspend fun trendingArticles(): List<TrendingArticle> {
    return trendingArticles
  }

  override suspend fun search(query: String): EntityPaginationItem<Article> {
    return dummyEntityPaginationItem
  }
}