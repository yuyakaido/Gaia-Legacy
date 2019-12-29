package com.yuyakaido.android.gaia.core.domain.entity

data class Comment(
  val id: ID,
  val body: String,
  val author: String,
  val created: Float
) : PaginationEntityType {
  data class ID(val value: String)
}