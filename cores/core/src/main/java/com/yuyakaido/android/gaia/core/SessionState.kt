package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

sealed class SessionState {

  abstract val id: String

  data class SignedOut(
    override val id: String
  ) : SessionState()

  data class SigningIn(
    override val id: String,
    val token: AuthToken?
  ) : SessionState()

  data class SignedIn(
    override val id: String,
    val token: AuthToken,
    val me: User.Detail.Me,
    val article: ArticleState = ArticleState(),
    val community: CommunityState = CommunityState.Initial
  ) : SessionState()

}