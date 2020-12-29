package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.reduxkit.ActionType

sealed class ArticleAction : ActionType<AppState> {

  abstract val source: ArticleListSource

  data class ToInitial(
    override val source: ArticleListSource
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        article = state.article.toInitial(this)
      )
    }
  }

  data class ToLoading(
    override val source: ArticleListSource
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        article = state.article.toLoading(this)
      )
    }
  }

  data class ToIdeal(
    override val source: ArticleListSource,
    val articles: List<Article>,
    val after: String?
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        article = state.article.toIdeal(this)
      )
    }
  }

  data class ToError(
    override val source: ArticleListSource
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        article = state.article.toError(this)
      )
    }
  }

  data class Update(
    override val source: ArticleListSource,
    val article: Article
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.update(
        article = state.article.update(this)
      )
    }
  }

}