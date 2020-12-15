package com.yuyakaido.android.gaia.vote

import com.yuyakaido.android.gaia.core.domain.entity.VotableType
import com.yuyakaido.android.gaia.core.domain.repository.VoteRepositoryType

class VoteRepository(
  private val api: VoteApi
) : VoteRepositoryType {

  override suspend fun <T : VotableType> upvote(
    entity: T
  ): T {
    return vote(VoteTarget.forUpvote(entity))
  }

  override suspend fun <T : VotableType> downvote(
    entity: T
  ): T {
    return vote(VoteTarget.forDownvote(entity))
  }

  private suspend fun <T : VotableType> vote(
    target: VoteTarget<T>
  ): T {
    api.vote(id = target.id(), dir = target.dir())
    return target.toVoted()
  }

}