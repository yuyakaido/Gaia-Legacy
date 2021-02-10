package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.reduxkit.StateType

data class ArticleState(
  val sources: Map<ArticleListSource, ArticleListState> = mapOf()
) : StateType {

  sealed class ArticleListState {
    abstract val articles: List<Article.ID>
    abstract val after: String?
    abstract fun canPaginate(): Boolean

    object Initial: ArticleListState() {
      override val articles: List<Article.ID> = emptyList()
      override val after: String? = null
      override fun canPaginate(): Boolean = true
    }
    data class Loading(
      override val articles: List<Article.ID>,
      override val after: String?
    ) : ArticleListState() {
      override fun canPaginate(): Boolean = false
    }
    data class Ideal(
      override val articles: List<Article.ID>,
      override val after: String?
    ) : ArticleListState() {
      override fun canPaginate(): Boolean = after?.isNotEmpty() ?: false
    }
    object Error : ArticleListState() {
      override val articles: List<Article.ID> = emptyList()
      override val after: String? = null
      override fun canPaginate(): Boolean = false
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
        articles = current.articles.plus(action.articles.map { it.id }),
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
          if (article == action.article.id) {
            action.article.id
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
