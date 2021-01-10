package com.yuyakaido.android.gaia.core.domain.entity

import com.yuyakaido.android.gaia.core.domain.value.AuthToken

sealed class Session {

  abstract val id: String
  abstract fun refreshToken(): String?

  data class SignedOut(
    override val id: String = System.nanoTime().toString()
  ) : Session() {
    override fun refreshToken(): String? {
      return null
    }
  }

  data class SigningIn(
    override val id: String
  ) : Session() {
    override fun refreshToken(): String? {
      return null
    }
  }

  data class SignedIn(
    override val id: String,
    val token: AuthToken
  ) : Session() {
    override fun refreshToken(): String? {
      return token.refreshToken
    }
    fun bearerToken(): String {
      return "bearer ${token.accessToken}"
    }
  }

}