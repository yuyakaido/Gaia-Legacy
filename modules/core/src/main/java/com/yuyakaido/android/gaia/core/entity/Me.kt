package com.yuyakaido.android.gaia.core.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Me(
  val id: String,
  val name: String,
  val icon: String,
  val birthday: Float,
  val karma: Int,
  val follower: Int
) : Parcelable