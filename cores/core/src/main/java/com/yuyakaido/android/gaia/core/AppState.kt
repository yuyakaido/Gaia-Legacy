package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

data class AppState(
  val article: ArticleState = ArticleState.Initial(),
  val community: CommunityState = CommunityState.Initial
) : StateType {

  sealed class ArticleState : StateType {
    abstract val articles: List<Article>
    abstract val after: String?
    data class Initial(
      override val articles: List<Article> = emptyList(),
      override val after: String? = null
    ) : ArticleState()
    data class Loading(
      override val articles: List<Article> = emptyList(),
      override val after: String? = null
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
    object Initial : CommunityState()
    object Loading : CommunityState()
    object Error : CommunityState()
    data class Ideal(val communities: List<Community.Detail>) : CommunityState()
  }

}