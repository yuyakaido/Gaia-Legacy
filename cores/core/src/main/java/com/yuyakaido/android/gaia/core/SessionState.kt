package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.AuthToken
import com.yuyakaido.android.reduxkit.StateType

sealed class SessionState : StateType {

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
    val domain: DomainState = DomainState(),
    val presentation: PresentationState = PresentationState()
  ) : SessionState() {
    override fun toEntity(): Session {
      return Session.SignedIn(
        id = id,
        token = token
      )
    }
    fun update(
      state: ArticleState,
      entities: List<Article>
    ): SignedIn {
      return copy(
        domain = domain.updateArticles(entities),
        presentation = presentation.update(state))
    }
    fun update(community: CommunityState): SignedIn {
      return copy(presentation = presentation.update(community))
    }
  }

}