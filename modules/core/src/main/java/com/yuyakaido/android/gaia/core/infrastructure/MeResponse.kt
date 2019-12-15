package com.yuyakaido.android.gaia.core.infrastructure

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.entity.Me

data class MeResponse(
  @Json(name = "id") val id: String,
  @Json(name = "name") val name: String,
  @Json(name = "icon_img") val icon: String,
  @Json(name = "created") val birthday: Float,
  @Json(name = "link_karma") val karma: Int,
  @Json(name = "num_friends") val follower: Int
) {
  fun toEntity(): Me {
    return Me(
      id = id,
      name = name,
      icon = icon,
      birthday = birthday,
      karma = karma,
      follower = follower
    )
  }
}