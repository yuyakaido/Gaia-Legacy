package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.value.AuthToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
  @SerialName("access_token") val accessToken: String,
  @SerialName("refresh_token") val refreshToken: String? = null
) {
  fun toAuthToken(): AuthToken {
    return AuthToken(
      accessToken = accessToken,
      refreshToken = refreshToken
    )
  }
}