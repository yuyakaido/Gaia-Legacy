package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.domain.app.AuthTokenServiceType
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
  private val service: AuthTokenServiceType,
  private val api: RedditWwwApi
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request? {
    var token = service.current()
    return token.refreshToken()?.let { refreshToken ->
      runBlocking {
        token = api.refreshToken(refreshToken = refreshToken).toAuthToken()
        service.save(token)
        response
          .request
          .newBuilder()
          .header("Authorization", token.bearerToken())
          .build()
      }
    } ?: response.request
  }

}