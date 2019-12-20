package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.domain.app.AuthTokenServiceType
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
  private val service: AuthTokenServiceType
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .apply {
          val token = service.current()
          if (token.isLoggedIn()) {
            addHeader("Authorization", token.bearerToken())
          } else {
            val credential = Credentials.basic(Constant.OAUTH_CLIENT_ID, "")
            addHeader("Authorization", credential)
          }
        }
        .build()
    )
  }

}