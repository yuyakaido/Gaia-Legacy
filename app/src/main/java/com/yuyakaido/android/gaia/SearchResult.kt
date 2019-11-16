package com.yuyakaido.android.gaia

import com.squareup.moshi.Json

data class SearchResult(
  @Json(name = "data") val data: Data
) {

  data class Data(
    @Json(name = "children") val children: List<Child>
  ) {
    data class Child(
      @Json(name = "data") val data: Data
    ) {
      data class Data(
        @Json(name = "id") val id: String,
        @Json(name = "title") val title: String
      ) {
        fun toEntity(): Subreddit {
          return Subreddit(
            id = Subreddit.ID(value = id),
            title = title
          )
        }
      }
    }
  }

  fun toEntities(): List<Subreddit> {
    return data
      .children
      .map { child -> child.data.toEntity() }
  }

}