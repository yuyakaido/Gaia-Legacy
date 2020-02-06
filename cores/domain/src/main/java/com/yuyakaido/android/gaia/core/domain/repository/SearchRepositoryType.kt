package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.SearchResult
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

interface SearchRepositoryType {
  suspend fun trendingArticles(): List<TrendingArticle>
  suspend fun search(query: String): EntityPaginationItem<SearchResult>
}