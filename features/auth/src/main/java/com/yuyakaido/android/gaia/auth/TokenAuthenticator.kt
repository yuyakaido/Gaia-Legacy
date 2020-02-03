package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
  private val repository: TokenRepositoryType
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request? {
    return runBlocking {
      val token = repository.refresh()
      repository.save(token)
      response
        .request
        .newBuilder()
        .header("Authorization", token.bearerToken())
        .build()
      }
    }

}