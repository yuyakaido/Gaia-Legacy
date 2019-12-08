package com.yuyakaido.android.gaia.core.infrastructure

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.entity.Me

data class MeResponse(
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
  @Json(name = "icon_img") val icon: String
) {
  fun toEntity(): Me {
    return Me(
      id = id,
      name = name,
      icon = icon
    )
  }
}