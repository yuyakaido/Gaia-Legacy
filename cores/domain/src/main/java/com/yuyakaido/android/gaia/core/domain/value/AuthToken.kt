package com.yuyakaido.android.gaia.core.domain.value

data class AuthToken(
  val accessToken: String,
  val refreshToken: String?
)