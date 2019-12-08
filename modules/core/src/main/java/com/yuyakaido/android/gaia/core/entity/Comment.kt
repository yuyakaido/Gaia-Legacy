package com.yuyakaido.android.gaia.core.entity

data class Comment(
  val id: ID,
  val body: String
) : EntityType {
  data class ID(val value: String)
}
