package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
  private val initial: Session,
  private val sessionRepository: SessionRepositoryType,
  private val tokenRepository: TokenRepositoryType
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request? {
    return runBlocking {
      val currentSession = sessionRepository.get(initial.id)
      val token = tokenRepository.refresh(currentSession)
      val newSession = Session.SignedIn(
        id = initial.id,
        token = token
      )
      sessionRepository.put(newSession)
      response
        .request
        .newBuilder()
        .header("Authorization", newSession.bearerToken())
        .build()
      }
    }

}