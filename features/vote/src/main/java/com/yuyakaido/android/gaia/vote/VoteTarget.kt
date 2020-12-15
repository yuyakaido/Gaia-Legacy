package com.yuyakaido.android.gaia.vote

import com.yuyakaido.android.gaia.core.domain.entity.VotableType

data class VoteTarget<T : VotableType>(
  private val entity: VotableType,
  private val dir: Int,
  private val likes: Boolean?
) {

  companion object {

    fun <T : VotableType> forUpvote(entity: VotableType): VoteTarget<T> {
      val pair: Pair<Int, Boolean?> = when (entity.likes) {
        null -> 1 to true
        true -> 0 to null
        false -> 1 to true
      }
      return VoteTarget(
        entity = entity,
        dir = pair.first,
        likes = pair.second
      )
    }

    fun <T : VotableType> forDownvote(entity: VotableType): VoteTarget<T> {
      val pair: Pair<Int, Boolean?> = when (entity.likes) {
        null -> -1 to false
        true -> -1 to false
        false -> 0 to null
      }
      return VoteTarget(
        entity = entity,
        dir = pair.first,
        likes = pair.second
      )
    }

  }

  fun id(): String {
    return entity.name
  }

  fun dir(): Int {
    return dir
  }

  fun toVoted(): T {
    return entity.toVoted(likes)
  }

}