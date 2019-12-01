package com.yuyakaido.android.gaia.core

import com.squareup.moshi.Json

data class TrendingResponse(
  @Json(name = "subreddit_names") val names: List<String>
) {
  fun toEntities(): List<TrendingSubreddit> {
    return names
      .map { name -> TrendingSubreddit(name = name) }
  }
}