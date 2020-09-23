package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

data class AppState(
  val article: ArticleState = ArticleState.Initial(),
  val community: CommunityState = CommunityState.Initial()
) : StateType {

  sealed class ArticleState : StateType {
    abstract val articles: List<Article>
    abstract val after: String?
    data class Initial(
      override val articles: List<Article> = emptyList(),
      override val after: String? = null
    ) : ArticleState()
    data class Loading(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleState()
    data class Error(
      override val articles: List<Article> = emptyList(),
      override val after: String? = null
    ) : ArticleState()
    data class Ideal(
      override val articles: List<Article>,
      override val after: String?
    ) : ArticleState()
  }

  sealed class CommunityState : StateType {
    abstract val communities: List<Community.Detail>
    abstract val after: String?
    abstract fun canPaginate(): Boolean
    data class Initial(
      override val communities: List<Community.Detail> = emptyList(),
      override val after: String? = null
    ) : CommunityState() {
      override fun canPaginate(): Boolean = true
    }
    data class Loading(
      override val communities: List<Community.Detail>,
      override val after: String?
    ) : CommunityState() {
      override fun canPaginate(): Boolean = false
    }
    data class Error(
      override val communities: List<Community.Detail> = emptyList(),
      override val after: String? = null
    ) : CommunityState() {
      override fun canPaginate(): Boolean = false
    }
    data class Ideal(
      override val communities: List<Community.Detail>,
      override val after: String?
    ) : CommunityState() {
      override fun canPaginate(): Boolean = after?.isNotEmpty() ?: false
    }
  }

}