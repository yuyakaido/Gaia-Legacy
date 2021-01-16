package com.yuyakaido.android.gaia.user.infrastructure.remote

import com.yuyakaido.android.gaia.user.infrastructure.local.MeSchema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeResponse(
  @SerialName("id") val id: String,
  @SerialName("name") val name: String,
  @SerialName("icon_img") val icon: String,
  @SerialName("created") val birthday: Float,
  @SerialName("link_karma") val karma: Int
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