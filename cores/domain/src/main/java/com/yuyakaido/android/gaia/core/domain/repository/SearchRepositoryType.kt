package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory
import com.yuyakaido.android.gaia.core.domain.value.SearchResult
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

interface SearchRepositoryType {
  suspend fun getTrendingArticles(): List<TrendingArticle>
  suspend fun getSearchHistories(): List<SearchHistory>
  suspend fun getSearchResult(query: String): EntityPaginationItem<SearchResult>
}