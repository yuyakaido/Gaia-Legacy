package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class SessionState {

  object SignedOut : SessionState()

  data class SignedIn(
    val article: ArticleState = ArticleState.Initial,
    val community: CommunityState = CommunityState.Initial
  ) : SessionState()

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