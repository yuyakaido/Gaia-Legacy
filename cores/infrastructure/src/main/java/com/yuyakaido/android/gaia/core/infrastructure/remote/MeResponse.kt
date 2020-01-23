package com.yuyakaido.android.gaia.core.infrastructure.remote

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.infrastructure.local.MeSchema

data class MeResponse(
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
  @Json(name = "icon_img") val icon: String,
  @Json(name = "created") val birthday: Float,
  @Json(name = "link_karma") val karma: Int
) {
  fun toSchema(): MeSchema {
    return MeSchema(
      id = id,
      name = name,
      icon = icon,
      birthday = birthday,
      karma = karma
    )
  }
}