package com.yuyakaido.android.gaia.core.domain.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
  val id: ID,
  override val name: String,
  val community: Community.Summary,
  val title: String,
  val thumbnail: Uri,
  val author: String,
  override val likes: Boolean?,
  val ups: Int,
  val downs: Int,
  val comments: Int
) : PaginatableType, VotableType, Parcelable {

  @Parcelize
  data class ID(val value: String) : Parcelable

  @IgnoredOnParcel
  val voteCount = ups - downs

  override fun <T : VotableType> toVoted(likes: Boolean?): T {
    return copy(likes = likes) as T
  }

}