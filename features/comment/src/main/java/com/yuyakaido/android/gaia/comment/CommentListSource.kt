package com.yuyakaido.android.gaia.comment

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import kotlinx.android.parcel.Parcelize

sealed class CommentListSource : Parcelable {

  abstract suspend fun comments(repository: CommentRepositoryType): List<Comment>

  @Parcelize
  data class Article(
    private val value: com.yuyakaido.android.gaia.core.domain.entity.Article
  ) : CommentListSource() {
    override suspend fun comments(repository: CommentRepositoryType): List<Comment> {
      return repository.comments(article = value)
    }
  }

  @Parcelize
  data class User(
    private val value: com.yuyakaido.android.gaia.core.domain.entity.User
  ) : CommentListSource() {
    override suspend fun comments(repository: CommentRepositoryType): List<Comment> {
      return repository.comments(user = value)
    }
  }

}