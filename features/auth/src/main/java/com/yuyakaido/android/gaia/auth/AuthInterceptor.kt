package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
  private val initial: Session,
  private val repository: SessionRepositoryType
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
      chain.request()
        .newBuilder()
        .apply {
          runBlocking {
            val s = repository.get(initial.id)
            addHeader("Authorization", s.bearerToken())
          }
        }
        .build()
    )
  }

}