package com.yuyakaido.android.storybook.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

class CommentRepositoryStorybook : CommentRepositoryType {

  private val comments = (0 until 10).map { index -> Comment.create(index.toString()) }

  override suspend fun comments(article: Article): List<Comment> {
    return comments
  }

  override suspend fun comments(user: User): List<Comment> {
    return comments
  }

  override suspend fun vote(target: VoteTarget) = Unit
}