package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User

interface CommentRepositoryType {
  suspend fun commentsByArticle(article: Article): List<Comment>
  suspend fun commentsByUser(user: User): List<Comment>
}