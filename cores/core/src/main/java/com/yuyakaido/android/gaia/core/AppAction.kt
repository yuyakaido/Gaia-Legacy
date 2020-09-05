package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class AppAction : ActionType<AppState> {

  sealed class ArticleAction : AppAction() {
    object ToInitial : ArticleAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          article = AppState.ArticleState.Initial()
        )
      }
    }
    data class ToLoading(val articles: List<Article>) : ArticleAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          article = AppState.ArticleState.Loading(
            articles = articles
          )
        )
      }
    }
    data class ToIdeal(val after: String?, val articles: List<Article>) : ArticleAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          article = AppState.ArticleState.Ideal(
            after = after,
            articles = state.article.articles.plus(articles)
          )
        )
      }
    }
    object ToError : ArticleAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          article = AppState.ArticleState.Error()
        )
      }
    }
    data class Update(val newArticle: Article) : ArticleAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          article = AppState.ArticleState.Ideal(
            after = state.article.after,
            articles = state.article.articles.map { oldArticle ->
              if (oldArticle.id == newArticle.id) {
                newArticle
              } else {
                oldArticle
              }
            }
          )
        )
      }
    }
  }

  sealed class CommunityAction : AppAction() {
    object ToLoading : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Loading
        )
      }
    }
    data class ToIdeal(val communities: List<Community.Detail>) : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Ideal(communities = communities)
        )
      }
    }
    object ToError : CommunityAction() {
      override fun reduce(state: AppState): AppState {
        return state.copy(
          community = AppState.CommunityState.Error
        )
      }
    }
  }

}