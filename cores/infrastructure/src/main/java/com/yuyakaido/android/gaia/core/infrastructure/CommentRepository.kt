package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import javax.inject.Inject

@AppScope
class CommentRepository @Inject constructor(
  private val api: PrivateApi
) {

  suspend fun comments(article: Article): List<Comment> {
    val response = api
      .comments(
        community = article.community.name,
        article = article.id.value
      )
    val responseOfComment = response.firstOrNull {
      it.data.children.any { child -> child.kind == Kind.Comment }
    }
    return responseOfComment?.toComments() ?: emptyList()
  }

}