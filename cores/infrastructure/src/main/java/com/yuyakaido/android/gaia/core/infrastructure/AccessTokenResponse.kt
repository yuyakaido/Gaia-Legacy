package com.yuyakaido.android.gaia.core.infrastructure

import com.squareup.moshi.Json

data class AccessTokenResponse(
  @Json(name = "access_token") val accessToken: String
)