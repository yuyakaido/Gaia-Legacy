package com.yuyakaido.android.gaia.core.infrastructure

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

data class AccessTokenResponse(
  @Json(name = "access_token") val accessToken: String,
  @Json(name = "refresh_token") val refreshToken: String?
) {

  fun toAuthToken(): AuthToken {
    return AuthToken(
      accessToken = accessToken,
      refreshToken = refreshToken
    )
  }

}