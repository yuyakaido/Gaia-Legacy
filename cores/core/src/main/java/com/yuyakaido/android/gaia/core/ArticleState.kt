package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource

data class ArticleState(
  val sources: Map<ArticleListSource, ArticleListState> = mapOf()
) {

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

  fun find(
    source: ArticleListSource
  ): ArticleListState {
    return sources[source] ?: ArticleListState.Initial
  }

  fun toInitial(
    action: ArticleAction.ToInitial
  ): ArticleState {
    return toState(
      source = action.source,
      state = ArticleListState.Initial
    )
  }

  fun toLoading(
    action: ArticleAction.ToLoading
  ): ArticleState {
    val current = find(action.source)
    return toState(
      source = action.source,
      state = ArticleListState.Loading(
        articles = current.articles,
        after = current.after
      )
    )
  }

  fun toIdeal(
    action: ArticleAction.ToIdeal,
  ): ArticleState {
    val current = find(action.source)
    return toState(
      source = action.source,
      state = ArticleListState.Ideal(
        articles = current.articles.plus(action.articles),
        after = action.after
      )
    )
  }

  fun toError(
    action: ArticleAction.ToError
  ): ArticleState {
    return toState(
      source = action.source,
      state = ArticleListState.Error
    )
  }

  fun update(
    action: ArticleAction.Update
  ): ArticleState {
    val current = find(action.source)
    return toState(
      source = action.source,
      state = ArticleListState.Ideal(
        articles = current.articles.map { article ->
          if (article.id == action.article.id) {
            action.article
          } else {
            article
          }
        },
        after = current.after
      )
    )
  }

  private fun toState(
    source: ArticleListSource,
    state: ArticleListState
  ): ArticleState {
    return copy(
      sources = sources.plus(source to state)
    )
  }

}
