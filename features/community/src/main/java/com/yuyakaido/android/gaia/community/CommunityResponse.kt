package com.yuyakaido.android.gaia.community

import android.net.Uri
import com.yuyakaido.android.gaia.core.domain.entity.Community
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityResponse(
  @SerialName("kind") val kind: String,
  @SerialName("data") val data: Data
) {

  @Serializable
  data class Data(
    @SerialName("id") val id: String,
    @SerialName("display_name") val name: String,
    @SerialName("icon_img") val icon: String,
    @SerialName("banner_background_image") val banner: String,
    @SerialName("subscribers") val subscribers: Int,
    @SerialName("user_is_subscriber") val isSubscriber: Boolean,
    @SerialName("public_description") val description: String
  )

  fun toEntity(): Community.Detail {
    return Community.Detail(
      id = data.id,
      name = data.name,
      icon = Uri.parse(data.icon),
      banner = Uri.parse(data.banner),
      subscribers = data.subscribers,
      isSubscriber = data.isSubscriber,
      description = data.description
    )
  }

}