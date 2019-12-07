package com.yuyakaido.android.gaia.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Me(
  val id: String,
  val name: String,
  val icon: String
) : Parcelable