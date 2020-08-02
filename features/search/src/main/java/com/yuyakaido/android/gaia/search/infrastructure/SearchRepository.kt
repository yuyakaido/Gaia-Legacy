package com.yuyakaido.android.gaia.search.infrastructure

import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory
import com.yuyakaido.android.gaia.core.domain.value.SearchResult
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

class SearchRepository(
  private val api: SearchApi,
  private val database: HistoryDatabase
) : SearchRepositoryType {

  override suspend fun getTrendingArticles(): List<TrendingArticle> {
    return api
      .trendingArticles()
      .toEntities()
  }

  override suspend fun getSearchHistories(): List<SearchHistory> {
    return database
      .historyDao()
      .findAll()
      .map { history -> history.toEntity() }
  }

  override suspend fun getSearchResult(query: String): EntityPaginationItem<SearchResult> {
    database.historyDao().insert(HistorySchema(name = query))
    return api
      .search(query = query)
      .toSearchResultItem()
  }

}