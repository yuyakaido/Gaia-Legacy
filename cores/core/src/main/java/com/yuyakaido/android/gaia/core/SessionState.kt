package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User

sealed class SessionState {

  abstract fun id(): String

  data class SignedOut(
    val state: String
  ) : SessionState() {
    override fun id(): String {
      return state
    }
  }

  data class SignedIn(
    val me: User.Detail.Me,
    val article: ArticleState = ArticleState.Initial,
    val community: CommunityState = CommunityState.Initial
  ) : SessionState() {
    override fun id(): String {
      return me.id
    }
  }

  sealed class ArticleState {
    abstract val articles: List<Article>
    abstract val after: String?

    object Initial: ArticleState() {
      override val articles: List<Article> = emptyList()
      override val after: String? = null
    }
    data class Loading(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleState()
    data class Ideal(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleState()
    object Error : ArticleState() {
      override val articles: List<Article> = emptyList()
      override val after: String? = null
    }
  }

  sealed class CommunityState {
    abstract val communities: List<Community.Detail>
    abstract val after: String?
    abstract fun canPaginate(): Boolean

    object Initial: CommunityState() {
      override val communities: List<Community.Detail> = emptyList()
      override val after: String? = null
      override fun canPaginate(): Boolean = true
    }
    data class Loading(
      override val communities: List<Community.Detail>,
      override val after: String?
    ) : CommunityState() {
      override fun canPaginate(): Boolean = false
    }
    data class Ideal(
      override val communities: List<Community.Detail>,
      override val after: String?
    ) : CommunityState() {
      override fun canPaginate(): Boolean = after?.isNotEmpty() ?: false
    }
    object Error: CommunityState() {
      override val communities: List<Community.Detail> = emptyList()
      override val after: String? = null
      override fun canPaginate(): Boolean = true
    }
  }

}