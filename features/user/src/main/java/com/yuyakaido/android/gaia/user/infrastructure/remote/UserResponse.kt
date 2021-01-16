package com.yuyakaido.android.gaia.user.infrastructure.remote

import com.yuyakaido.android.gaia.core.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
  @SerialName("kind") val kind: String,
  @SerialName("data") val data: Data
) {
  @Serializable
  data class Data(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon_img") val icon: String,
    @SerialName("created") val birthday: Float,
    @SerialName("link_karma") val karma: Int
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