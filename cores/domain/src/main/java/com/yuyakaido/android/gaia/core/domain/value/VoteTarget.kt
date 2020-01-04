package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.VotableEntityType

data class VoteTarget(
  val entity: VotableEntityType,
  val dir: Int,
  val likes: Boolean?
) {

  companion object {

    fun forUpvote(entity: VotableEntityType): VoteTarget {
      val pair: Pair<Int, Boolean?> = when {
        entity.likes == null -> 1 to true
        entity.likes == true -> 0 to null
        entity.likes == false -> 1 to true
        else -> 0 to null
      }
      return VoteTarget(
        entity = entity,
        dir = pair.first,
        likes = pair.second
      )
    }

    fun forDownvote(entity: VotableEntityType): VoteTarget {
      val pair: Pair<Int, Boolean?> = when {
        entity.likes == null -> -1 to false
        entity.likes == true -> -1 to false
        entity.likes == false -> 0 to null
        else -> 0 to null
      }
      return VoteTarget(
        entity = entity,
        dir = pair.first,
        likes = pair.second
      )
    }

  }

  fun article(): Article {
    return (entity as Article).copy(likes = likes)
  }

}