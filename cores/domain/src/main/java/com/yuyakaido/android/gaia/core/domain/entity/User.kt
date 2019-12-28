package com.yuyakaido.android.gaia.core.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class User : Parcelable {

  abstract val id: String
  abstract val name: String

  @Parcelize
  data class Summary(
    override val id: String,
    override val name: String
  ) : User()

  @Parcelize
  data class Detail(
    override val id: String,
    override val name: String,
    val icon: String,
    val birthday: Float,
    val karma: Int
  ) : User()

  @Parcelize
  data class Me(
    override val id: String,
    override val name: String,
    val icon: String,
    val birthday: Float,
    val karma: Int
  ) : User()

}