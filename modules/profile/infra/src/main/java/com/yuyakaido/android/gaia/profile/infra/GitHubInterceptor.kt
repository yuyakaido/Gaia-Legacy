package com.yuyakaido.android.gaia.profile.infra

import com.yuyakaido.android.gaia.core.java.CurrentSession
import com.yuyakaido.android.gaia.core.java.SessionState
import okhttp3.Interceptor
import okhttp3.Response

class GitHubInterceptor(
    private val currentSession: CurrentSession
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val session = currentSession.getCurrentSession()
        return if (session.isLoggedOut()) {
            chain.proceed(originalRequest)
        } else {
            val token = if (session is SessionState.Resolved.LoggedIn) {
                session.token
            } else {
                ""
            }
            val newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "token $token")
                .build()
            chain.proceed(newRequest)
        }
    }

}