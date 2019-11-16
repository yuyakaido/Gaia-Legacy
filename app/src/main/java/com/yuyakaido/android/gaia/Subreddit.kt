package com.yuyakaido.android.gaia

import android.net.Uri

data class Subreddit(
  val id: ID,
  val title: String,
  val thumbnail: Uri
) {
  data class ID(val value: String)
}