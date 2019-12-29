package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

interface CommentRepositoryType {
  suspend fun comments(article: Article): List<Comment>
  suspend fun comments(user: User): List<Comment>
  suspend fun vote(target: VoteTarget)
}