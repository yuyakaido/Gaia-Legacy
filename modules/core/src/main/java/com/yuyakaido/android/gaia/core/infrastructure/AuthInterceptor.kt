package com.yuyakaido.android.gaia.core.infrastructure

import android.app.Application
import com.yuyakaido.android.gaia.core.value.AccessToken
import com.yuyakaido.android.gaia.core.value.Constant
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
  private val application: Application
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .apply {
          val token = AccessToken.current(application)
          if (token.isLoggedIn()) {
            addHeader("Authorization", token.value())
          } else {
            val credential = Credentials.basic(Constant.OAUTH_CLIENT_ID, "")
            addHeader("Authorization", credential)
          }
        }
        .build()
    )
  }

}