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
) : PaginationEntityType, VotableEntityType, Parcelable {

  @Parcelize
  data class ID(val value: String) : Parcelable

  @IgnoredOnParcel
  val voteCount = ups - downs

  companion object {
    fun create(id: String): Article {
      return Article(
        id = ID(id),
        name = "name",
        community = Community.Summary(name = "category"),
        title = "title",
        thumbnail = Uri.parse("https://dummyimage.com/600x400/000/fff"),
        author = "yuyakaido",
        likes = null,
        ups = 0,
        downs = 0,
        comments = 0
      )
    }
  }

}