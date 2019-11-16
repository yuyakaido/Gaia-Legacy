package com.yuyakaido.android.gaia

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subreddit(
  val id: ID,
  val title: String,
  val thumbnail: Uri
) : Parcelable {
  @Parcelize
  data class ID(val value: String) : Parcelable
}