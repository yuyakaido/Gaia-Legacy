package com.yuyakaido.android.gaia.core.domain.value

data class AccessToken(
  private val value: String?
) {

  fun isLoggedIn(): Boolean {
    return value != null
  }

  fun rawValue(): String? {
    return value
  }

  fun bearerValue(): String {
    return "bearer $value"
  }

}