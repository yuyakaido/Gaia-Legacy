package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.Kind
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi

class CommentRepository(
  private val api: PrivateApi
) : CommentRepositoryType {

  override suspend fun comments(article: Article): List<Comment> {
    val response = api
      .commentsOfArticle(
        community = article.community.name,
        article = article.id.value
      )
    val responseOfComment = response.firstOrNull {
      it.data.children.any { child -> child.kind == Kind.Comment }
    }
    return responseOfComment?.toComments() ?: emptyList()
  }

  override suspend fun comments(user: User): List<Comment> {
    return api
      .commentsOfUser(user = user.name)
      .toComments()
  }

  override suspend fun vote(target: VoteTarget) {
    api.vote(id = target.entity.name, dir = target.dir)
  }

}