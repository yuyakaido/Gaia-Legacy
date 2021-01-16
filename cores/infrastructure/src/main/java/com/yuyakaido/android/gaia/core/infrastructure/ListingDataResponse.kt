package com.yuyakaido.android.gaia.core.infrastructure

import android.net.Uri
import android.webkit.URLUtil
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.SearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://www.reddit.com/dev/api/
@Serializable
data class ListingDataResponse(
  @SerialName("data") val data: Children
) {
  @Serializable
  data class Children(
    @SerialName("children") val children: List<Child>,
    @SerialName("before") val before: String?,
    @SerialName("after") val after: String?
  ) {
    @Serializable
    sealed class Child {
      @Serializable
      sealed class Data {
        @Serializable
        data class Comment(
          @SerialName("id") val id: String,
          @SerialName("name") val name: String,
          @SerialName("body") val body: String,
          @SerialName("author") val author: String,
          @SerialName("created") val created: Float,
          @SerialName("likes") val likes: Boolean?,
          @SerialName("ups") val ups: Int,
          @SerialName("downs") val downs: Int
        ) : Data()
        @Serializable
        data class Article(
          @SerialName("id") val id: String,
          @SerialName("name") val name: String,
          @SerialName("subreddit") val community: String,
          @SerialName("title") val title: String,
          @SerialName("thumbnail") val thumbnail: String?,
          @SerialName("author") val author: String,
          @SerialName("likes") val likes: Boolean?,
          @SerialName("ups") val ups: Int,
          @SerialName("downs") val downs: Int,
          @SerialName("num_comments") val comments: Int
        ) : Data()
        @Serializable
        data class Community(
          @SerialName("id") val id: String,
          @SerialName("display_name") val name: String,
          @SerialName("icon_img") val icon: String,
          @SerialName("banner_background_image") val banner: String,
          @SerialName("subscribers") val subscribers: Int,
          @SerialName("user_is_subscriber") val isSubscriber: Boolean,
          @SerialName("public_description") val description: String
        ) : Data()
        @Serializable
        data class More(
          @SerialName("id") val id: String,
          @SerialName("name") val name: String,
          @SerialName("count") val count: Int
        ) : Data()
      }
      @Serializable
      @SerialName(Kind.comment)
      data class Comment(
        @SerialName("data") override val data: Data.Comment
      ) : Child() {
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
      @Serializable
      @SerialName(Kind.article)
      data class Article(
        @SerialName("data") override val data: Data.Article
      ) : Child() {
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
      @Serializable
      @SerialName(Kind.community)
      data class Community(
        @SerialName("data") override val data: Data.Community
      ) : Child() {
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
      @Serializable
      @SerialName(Kind.more)
      data class More(
        @SerialName("data") override val data: Data.More
      ) : Child()
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
  fun toSearchResultItem(): EntityPaginationItem<SearchResult> {
    return EntityPaginationItem(
      entities = data
        .children
        .filterIsInstance<Children.Child.Article>()
        .map { article -> SearchResult(article = article.toEntity()) },
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
