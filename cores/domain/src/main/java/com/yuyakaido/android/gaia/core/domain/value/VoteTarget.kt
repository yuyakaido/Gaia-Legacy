package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.Article

data class VoteTarget(
  val article: Article,
  val dir: Int,
  val likes: Boolean?
) {

  companion object {

    fun forUpvote(article: Article): VoteTarget {
      val pair: Pair<Int, Boolean?> = when {
        article.likes == null -> 1 to true
        article.likes == true -> 0 to null
        article.likes == false -> 1 to true
        else -> 0 to null
      }
      return VoteTarget(
        article = article,
        dir = pair.first,
        likes = pair.second
      )
    }

    fun forDownvote(article: Article): VoteTarget {
      val pair: Pair<Int, Boolean?> = when {
        article.likes == null -> -1 to false
        article.likes == true -> -1 to false
        article.likes == false -> 0 to null
        else -> 0 to null
      }
      return VoteTarget(
        article = article,
        dir = pair.first,
        likes = pair.second
      )
    }

  }

}