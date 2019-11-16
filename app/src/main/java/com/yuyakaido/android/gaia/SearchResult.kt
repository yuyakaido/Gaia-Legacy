package com.yuyakaido.android.gaia

import android.net.Uri
import android.webkit.URLUtil
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
        @Json(name = "title") val title: String,
        @Json(name = "thumbnail") val thumbnail: String
      ) {
        private fun toUri(): Uri {
          return if (URLUtil.isNetworkUrl(thumbnail)) {
            Uri.parse(thumbnail)
          } else {
            Uri.EMPTY
          }
        }
        fun toEntity(): Subreddit {
          return Subreddit(
            id = Subreddit.ID(value = id),
            title = title,
            thumbnail = toUri()
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