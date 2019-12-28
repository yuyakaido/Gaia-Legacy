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

  sealed class Detail : User() {

    abstract val icon: String
    abstract val birthday: Float
    abstract val karma: Int

    @Parcelize
    data class Me(
      override val id: String,
      override val name: String,
      override val icon: String,
      override val birthday: Float,
      override val karma: Int
    ) : User.Detail()

    @Parcelize
    data class Other(
      override val id: String,
      override val name: String,
      override val icon: String,
      override val birthday: Float,
      override val karma: Int
    ) : User.Detail()

  }

}