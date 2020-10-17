package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

class TokenRepository(
  private val api: AuthApi
) : TokenRepositoryType {

  override suspend fun get(code: String): AuthToken {
    val response = api.getInitialToken(code = code)
    return response.toAuthToken()
  }

  override suspend fun refresh(session: Session): AuthToken {
    session.refreshToken()?.let { refreshToken ->
      val response = api.refreshToken(refreshToken = refreshToken)
      return response.toAuthToken()
    } ?: throw AssertionError()
  }

}