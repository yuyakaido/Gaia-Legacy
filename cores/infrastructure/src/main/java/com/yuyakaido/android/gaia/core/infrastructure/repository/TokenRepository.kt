package com.yuyakaido.android.gaia.core.infrastructure.repository

import android.app.Application
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.AuthToken
import com.yuyakaido.android.gaia.core.infrastructure.remote.api.PublicApi

class TokenRepository(
  private val application: Application,
  private val api: PublicApi
) : TokenRepositoryType {

  companion object {
    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"
  }

  private val preference = PreferenceManager.getDefaultSharedPreferences(application)

  override suspend fun get(code: String): AuthToken {
    val response = api.getInitialToken(code = code)
    return response.toAuthToken()
  }

  override suspend fun get(): AuthToken {
    return AuthToken(
      accessToken = preference.getString(ACCESS_TOKEN, null),
      refreshToken = preference.getString(REFRESH_TOKEN, null)
    )
  }

  override suspend fun refresh(): AuthToken {
    val token = get()
    token.refreshToken()?.let { refreshToken ->
      val response = api.refreshToken(refreshToken = refreshToken)
      return response.toAuthToken()
    } ?: throw AssertionError()
  }

  override suspend fun save(token: AuthToken) {
    preference.edit(commit = false) {
      token.accessToken()?.let { accessToken ->
        putString(ACCESS_TOKEN, accessToken)
      }
      token.refreshToken()?.let { refreshToken ->
        putString(REFRESH_TOKEN, refreshToken)
      }
    }
  }

}