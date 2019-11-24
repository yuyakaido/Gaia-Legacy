package com.yuyakaido.android.gaia

import android.net.Uri
import android.webkit.URLUtil
import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.Subreddit

data class SubredditListResponse(
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
        @Json(name = "name") val name: String,
        @Json(name = "subreddit_name_prefixed") val category: String,
        @Json(name = "title") val title: String,
        @Json(name = "thumbnail") val thumbnail: String?,
        @Json(name = "author") val author: String,
        @Json(name = "likes") val likes: Boolean?,
        @Json(name = "ups") val ups: Int,
        @Json(name = "downs") val downs: Int,
        @Json(name = "num_comments") val comments: Int
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
            name = name,
            category = category,
            title = title,
            thumbnail = toUri(),
            author = author,
            likes = likes,
            ups = ups,
            downs = downs,
            comments = comments
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