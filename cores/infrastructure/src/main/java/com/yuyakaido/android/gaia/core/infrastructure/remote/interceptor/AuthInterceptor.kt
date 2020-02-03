package com.yuyakaido.android.gaia.core.infrastructure.remote.interceptor

import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.app.Constant
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
  private val repository: TokenRepositoryType
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .apply {
          runBlocking {
            val token = repository.get()
            if (token.isLoggedIn()) {
              addHeader("Authorization", token.bearerToken())
            } else {
              val credential = Credentials.basic(Constant.OAUTH_CLIENT_ID, "")
              addHeader("Authorization", credential)
            }
          }
        }
        .build()
    )
  }

}