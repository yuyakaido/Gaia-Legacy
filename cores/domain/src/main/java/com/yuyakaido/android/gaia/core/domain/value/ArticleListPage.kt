package com.yuyakaido.android.gaia.core.domain.value

import android.os.Parcelable
import com.yuyakaido.android.gaia.core.domain.entity.Community
import kotlinx.android.parcel.Parcelize

sealed class ArticleListPage : Parcelable {

  abstract fun path(): String

  @Parcelize
  object Popular : ArticleListPage(), Parcelable {
    override fun path(): String {
      return "popular"
    }
  }

  @Parcelize
  data class CommunityDetail(
    val community: Community.Summary
  ) : ArticleListPage(), Parcelable {
    override fun path(): String {
      return community.name
    }
  }

}