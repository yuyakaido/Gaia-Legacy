package com.yuyakaido.android.gaia.search.presentation

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

data class TrendingArticleResponse(
  @Json(name = "subreddit_names") val names: List<String>
) {
  fun toEntities(): List<TrendingArticle> {
    return names
      .map { name ->
        TrendingArticle(
          name = name
        )
      }
  }
}