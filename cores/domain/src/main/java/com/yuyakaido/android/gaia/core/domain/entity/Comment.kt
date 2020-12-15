package com.yuyakaido.android.gaia.core.domain.entity

data class Comment(
  val id: ID,
  override val name: String,
  val body: String,
  val author: String,
  val created: Float,
  override val likes: Boolean?,
  val ups: Int,
  val downs: Int
) : PaginatableType, VotableType {

  data class ID(val value: String)

  override fun <T : VotableType> toVoted(likes: Boolean?): T {
    return copy(likes = likes) as T
  }

}