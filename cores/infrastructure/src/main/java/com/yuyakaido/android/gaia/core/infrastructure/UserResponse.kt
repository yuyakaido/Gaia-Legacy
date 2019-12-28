package com.yuyakaido.android.gaia.core.infrastructure

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.entity.User

data class UserResponse(
  @Json(name = "kind") val kind: String,
  @Json(name = "data") val data: Data
) {
  data class Data(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "icon_img") val icon: String,
    @Json(name = "created") val birthday: Float,
    @Json(name = "link_karma") val karma: Int
  )
  fun toEntity(): User.Detail {
    return User.Detail.Other(
      id = data.id,
      name = data.name,
      icon = data.icon,
      birthday = data.birthday,
      karma = data.karma
    )
  }
}