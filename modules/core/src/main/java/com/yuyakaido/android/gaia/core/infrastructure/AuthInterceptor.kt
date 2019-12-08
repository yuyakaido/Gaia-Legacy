package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain
      .proceed(
        chain
          .request()
          .newBuilder()
          .addHeader("Authorization", BuildConfig.REDDIT_ACCESS_TOKEN)
          .build()
      )
  }

}