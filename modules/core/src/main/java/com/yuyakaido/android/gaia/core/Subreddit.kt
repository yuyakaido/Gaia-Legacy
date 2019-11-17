package com.yuyakaido.android.gaia.core

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subreddit(
  val id: ID,
  val name: String,
  val title: String,
  val thumbnail: Uri,
  val author: String
) : Parcelable {
  @Parcelize
  data class ID(val value: String) : Parcelable
}