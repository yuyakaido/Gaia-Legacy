package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.gaia.core.domain.entity.Community
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

  data class ArticleState(
    val sources: MutableMap<ArticleListSource, ArticleListState> = mutableMapOf()
  ) {
    fun find(source: ArticleListSource): ArticleListState {
      return sources[source] ?: ArticleListState.Initial
    }
    fun update(
      source: ArticleListSource,
      state: ArticleListState
    ): ArticleState {
      return apply {
        sources[source] = state
      }
    }
  }

  sealed class ArticleListState {
    abstract val articles: List<Article>
    abstract val after: String?

    object Initial: ArticleListState() {
      override val articles: List<Article> = emptyList()
      override val after: String? = null
    }
    data class Loading(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleListState()
    data class Ideal(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleListState()
    object Error : ArticleListState() {
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