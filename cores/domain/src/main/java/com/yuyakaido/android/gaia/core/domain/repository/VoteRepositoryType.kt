package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.VotableType

interface VoteRepositoryType {
  suspend fun <T : VotableType> upvote(entity: T): T
  suspend fun <T : VotableType> downvote(entity: T): T
}