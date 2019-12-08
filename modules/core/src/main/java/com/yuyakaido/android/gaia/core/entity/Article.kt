package com.yuyakaido.android.gaia.core.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
  val id: ID,
  val name: String,
  val category: String,
  val title: String,
  val thumbnail: Uri,
  val author: String,
  val likes: Boolean?,
  val ups: Int,
  val downs: Int,
  val comments: Int
) : EntityType, Parcelable {

  @Parcelize
  data class ID(val value: String) : Parcelable

  @IgnoredOnParcel
  val voteCount = ups - downs

}