package com.yuyakaido.android.gaia.core.domain.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Community {
  @Parcelize
  data class Summary(
    val name: String
  ) : Community(), Parcelable
  @Parcelize
  data class Detail(
    val id: String,
    val name: String,
    val icon: Uri,
    val banner: Uri,
    val subscribers: Int,
    val isSubscriber: Boolean,
    val description: String
  ) : Community(), Parcelable
}