package com.yuyakaido.android.gaia.core.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
  val id: String,
  val name: String
) : Parcelable