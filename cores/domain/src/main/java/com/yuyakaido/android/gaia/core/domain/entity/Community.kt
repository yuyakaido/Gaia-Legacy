package com.yuyakaido.android.gaia.core.domain.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Community : PaginatableType, Parcelable {

  @Parcelize
  data class Name(val value: String) : Parcelable

  abstract fun name(): Name
  abstract fun toSummary(): Summary

  @Parcelize
  data class Summary(
    val name: Name
  ) : Community() {
    override fun name(): Name = name
    override fun toSummary(): Summary = this
  }

  @Parcelize
  data class Detail(
    val id: ID,
    val name: Name,
    val icon: Uri,
    val banner: Uri,
    val subscribers: Int,
    val isSubscriber: Boolean,
    val description: String
  ) : Community() {
    @Parcelize
    data class ID(val value: String) : Parcelable
    override fun name(): Name = name
    override fun toSummary(): Summary = Summary(name = name)
  }

}