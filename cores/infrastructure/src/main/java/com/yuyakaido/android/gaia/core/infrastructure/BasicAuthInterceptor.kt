package com.yuyakaido.android.gaia.core.infrastructure

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .apply {
          val credential = Credentials.basic(Constant.OAUTH_CLIENT_ID, "")
          addHeader("Authorization", credential)
        }
        .build()
    )
  }

}