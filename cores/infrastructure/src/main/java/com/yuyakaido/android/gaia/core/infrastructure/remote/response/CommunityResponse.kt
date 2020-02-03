package com.yuyakaido.android.gaia.core.infrastructure.remote.response

import android.net.Uri
import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.entity.Community

data class CommunityResponse(
  @Json(name = "kind") val kind: String,
  @Json(name = "data") val data: Data
) {

  data class Data(
    @Json(name = "id") val id: String,
    @Json(name = "display_name") val name: String,
    @Json(name = "icon_img") val icon: String,
    @Json(name = "banner_background_image") val banner: String,
    @Json(name = "subscribers") val subscribers: Int,
    @Json(name = "user_is_subscriber") val isSubscriber: Boolean,
    @Json(name = "public_description") val description: String
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