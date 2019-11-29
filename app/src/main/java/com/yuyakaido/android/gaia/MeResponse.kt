package com.yuyakaido.android.gaia

import com.squareup.moshi.Json

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