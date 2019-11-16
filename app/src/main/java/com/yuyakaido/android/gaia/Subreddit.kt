package com.yuyakaido.android.gaia

data class Subreddit(
  val id: ID,
  val title: String
) {
  data class ID(val value: String)
}