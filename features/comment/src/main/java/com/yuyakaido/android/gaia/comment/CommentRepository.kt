package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.ListingDataResponse

class CommentRepository(
  private val api: CommentApi
) : CommentRepositoryType {

  override suspend fun commentsByArticle(article: Article): List<Comment> {
    val response = api
      .commentsOfArticle(
        community = article.community.name,
        article = article.id.value
      )
    val responseOfComment = response.firstOrNull {
      it.data.children.any { child -> child is ListingDataResponse.Children.Child.Comment }
    }
    return responseOfComment?.toComments() ?: emptyList()
  }

  override suspend fun commentsByUser(user: User): List<Comment> {
    return api
      .commentsOfUser(user = user.name)
      .toComments()
  }

}