package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

sealed class SessionState {

  abstract val id: String
  abstract fun toEntity(): Session

  data class SignedOut(
    override val id: String
  ) : SessionState() {
    override fun toEntity(): Session {
      return Session.SignedOut(
        id = id
      )
    }
  }

  data class SigningIn(
    override val id: String,
    val token: AuthToken?
  ) : SessionState() {
    override fun toEntity(): Session {
      return Session.SigningIn(
        id = id
      )
    }
  }

  data class SignedIn(
    override val id: String,
    val token: AuthToken,
    val me: User.Detail.Me,
    val article: ArticleState = ArticleState(),
    val community: CommunityState = CommunityState.Initial
  ) : SessionState() {
    override fun toEntity(): Session {
      return Session.SignedIn(
        id = id,
        token = token
      )
    }
  }

}