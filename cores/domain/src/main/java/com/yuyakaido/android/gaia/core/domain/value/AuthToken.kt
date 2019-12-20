package com.yuyakaido.android.gaia.core.domain.value

data class AuthToken(
  private val accessToken: String?,
  private val refreshToken: String?
) {

  fun isLoggedIn(): Boolean {
    return accessToken != null
  }

  fun accessToken(): String? {
    return accessToken
  }

  fun refreshToken(): String? {
    return refreshToken
  }

  fun bearerToken(): String {
    return "bearer $accessToken"
  }

}