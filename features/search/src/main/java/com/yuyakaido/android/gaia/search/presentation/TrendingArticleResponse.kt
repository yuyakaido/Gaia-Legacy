package com.yuyakaido.android.gaia.search.presentation

import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingArticleResponse(
  @SerialName("subreddit_names") val names: List<String>
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