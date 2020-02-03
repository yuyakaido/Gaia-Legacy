package com.yuyakaido.android.gaia.core.infrastructure.remote.response

import android.net.Uri
import android.webkit.URLUtil
import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem

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

      sealed class Data {
        data class Comment(
          @Json(name = "id") val id: String,
          @Json(name = "name") val name: String,
          @Json(name = "body") val body: String,
          @Json(name = "author") val author: String,
          @Json(name = "created") val created: Float,
          @Json(name = "likes") val likes: Boolean?,
          @Json(name = "ups") val ups: Int,
          @Json(name = "downs") val downs: Int
        ) : Data()
        data class Article(
          @Json(name = "id") val id: String,
          @Json(name = "name") val name: String,
          @Json(name = "subreddit") val community: String,
          @Json(name = "title") val title: String,
          @Json(name = "thumbnail") val thumbnail: String?,
          @Json(name = "author") val author: String,
          @Json(name = "likes") val likes: Boolean?,
          @Json(name = "ups") val ups: Int,
          @Json(name = "downs") val downs: Int,
          @Json(name = "num_comments") val comments: Int
        ) : Data()
        data class Community(
          @Json(name = "id") val id: String,
          @Json(name = "display_name") val name: String,
          @Json(name = "icon_img") val icon: String,
          @Json(name = "banner_background_image") val banner: String,
          @Json(name = "subscribers") val subscribers: Int,
          @Json(name = "user_is_subscriber") val isSubscriber: Boolean,
          @Json(name = "public_description") val description: String
        ) : Data()
        data class More(
          @Json(name = "id") val id: String,
          @Json(name = "name") val name: String,
          @Json(name = "count") val count: String
        ) : Data()
      }
      data class Comment(
        @Json(name = "data") override val data: Data.Comment
      ) : Child(
        Kind.Comment
      ) {
        fun toEntity(): com.yuyakaido.android.gaia.core.domain.entity.Comment {
          return Comment(
            id = com.yuyakaido.android.gaia.core.domain.entity.Comment.ID(value = data.id),
            name = data.name,
            body = data.body,
            author = data.author,
            created = data.created,
            likes = data.likes,
            ups = data.ups,
            downs = data.downs
          )
        }
      }
      data class Article(
        @Json(name = "data") override val data: Data.Article
      ) : Child(
        Kind.Article
      ) {
        fun toEntity(): com.yuyakaido.android.gaia.core.domain.entity.Article {
          return Article(
            id = com.yuyakaido.android.gaia.core.domain.entity.Article.ID(value = data.id),
            name = data.name,
            community = com.yuyakaido.android.gaia.core.domain.entity.Community.Summary(name = data.community),
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
      data class Community(
        @Json(name = "data") override val data: Data.Community
      ) : Child(
        Kind.Community
      ) {
        fun toEntity(): com.yuyakaido.android.gaia.core.domain.entity.Community.Detail {
          return com.yuyakaido.android.gaia.core.domain.entity.Community.Detail(
            id = data.id,
            name = data.name,
            icon = Uri.parse(data.icon),
            banner = Uri.parse(data.banner),
            subscribers = data.subscribers,
            isSubscriber = data.isSubscriber,
            description = data.description
          )
        }
      }
      data class More(
        @Json(name = "data") override val data: Data.More
      ) : Child(
        Kind.More
      )
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
  fun toCommunityPaginationItem(): EntityPaginationItem<Community.Detail> {
    return EntityPaginationItem(
      entities = data
        .children
        .filterIsInstance<Children.Child.Community>()
        .map { community -> community.toEntity() },
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
