package com.yuyakaido.android.gaia.core

import android.net.Uri
import android.webkit.URLUtil
import com.squareup.moshi.Json

// https://www.reddit.com/dev/api/
data class ListingDataResponse(
  @Json(name = "data") val data: Children
) {
  data class Children(
    @Json(name = "children") val children: List<Child>,
    @Json(name = "before") val before: String?,
    @Json(name = "after") val after: String?
  ) {
    sealed class Child(
      @Json(name = "kind") val kind: Kind
    ) {
      enum class Kind(val id: String) {
        Comment(id = "t1"),
        Account(id = "t2"),
        Article(id = "t3"),
        Message(id = "t4"),
        Subreddit(id = "t5"),
        Award(id = "t6")
      }
      sealed class Data {
        data class Comment(
          @Json(name = "id") val id: String,
          @Json(name = "body") val body: String
        ) : Data()
        data class Article(
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
      ) : Child(Kind.Comment) {
        fun toEntity(): com.yuyakaido.android.gaia.core.Comment {
          return Comment(
            id = com.yuyakaido.android.gaia.core.Comment.ID(value = data.id),
            body = data.body
          )
        }
      }
      data class Article(
        @Json(name = "data") override val data: Data.Article
      ) : Child(Kind.Article) {
        fun toEntity(): com.yuyakaido.android.gaia.core.Article {
          return Article(
            id = com.yuyakaido.android.gaia.core.Article.ID(value = data.id),
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
  fun toArticlePaginationItem(): EntityPaginationItem<Article> {
    return EntityPaginationItem(
      entities = data
        .children
        .filterIsInstance<Children.Child.Article>()
        .map { article -> article.toEntity() },
      before = data.before,
      after = data.after
    )
  }
  fun toComments(): List<Comment> {
    return data.children
      .filterIsInstance<Children.Child.Comment>()
      .map { it.toEntity() }
  }
}
