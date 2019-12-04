package com.yuyakaido.android.gaia.core

data class Comment(
  val id: ID,
  val body: String
) {
  data class ID(val value: String)
}
