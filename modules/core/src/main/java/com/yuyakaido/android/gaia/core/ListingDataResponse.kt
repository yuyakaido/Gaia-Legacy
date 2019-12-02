package com.yuyakaido.android.gaia.core

import android.net.Uri
import android.webkit.URLUtil
import com.squareup.moshi.Json

data class ListingDataResponse(
  @Json(name = "data") val data: Children
) {
  data class Children(
    @Json(name = "children") val children: List<Child>
  ) {
    sealed class Child(
      @Json(name = "kind") val kind: Kind
    ) {
      enum class Kind {
        t1, t2, t3, t4, t5
      }
      sealed class Data {
        data class Comment(
          @Json(name = "id") val id: String,
          @Json(name = "body") val body: String
        ) : Data()
        data class Subreddit(
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
        ) : Data()
      }
      data class Comment(
        @Json(name = "data") override val data: Data.Comment
      ) : Child(Kind.t1)
      data class Article(
        @Json(name = "data") override val data: Data.Subreddit
      ) : Child(Kind.t3) {
        fun toEntity(): Subreddit {
          return Subreddit(
            id = Subreddit.ID(value = data.id),
            name = data.name,
            category = data.category,
            title = data.title,
            thumbnail = toUri(),
            author = data.author,
            likes = data.likes,
            ups = data.ups,
            downs = data.downs,
            comments = data.comments
          )
        }
        private fun toUri(): Uri {
          return if (URLUtil.isNetworkUrl(data.thumbnail)) {
            Uri.parse(data.thumbnail)
          } else {
            Uri.EMPTY
          }
        }
      }
      abstract val data: Data
    }
  }
  fun toEntities(): List<Subreddit> {
    return data
      .children
      .filterIsInstance<Children.Child.Article>()
      .map { it.toEntity() }
  }
}
