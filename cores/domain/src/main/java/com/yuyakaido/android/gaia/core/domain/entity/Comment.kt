package com.yuyakaido.android.gaia.core.domain.entity

data class Comment(
  val id: ID,
  val body: String
) : PaginationEntityType {
  data class ID(val value: String)
}