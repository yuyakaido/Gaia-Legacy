package com.yuyakaido.android.gaia.search

import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.SearchResult
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

class SearchRepository(
  private val api: SearchApi
) : SearchRepositoryType {

  override suspend fun trendingArticles(): List<TrendingArticle> {
    return api
      .trendingArticles()
      .toEntities()
  }

  override suspend fun search(query: String): EntityPaginationItem<SearchResult> {
    return api
      .search(query = query)
      .toSearchResultItem()
  }

}