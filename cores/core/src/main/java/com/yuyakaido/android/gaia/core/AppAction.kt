package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class AppAction : ActionType<AppState>

sealed class ArticleAction : AppAction() {
  object ToInitial : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        article = AppState.ArticleState.Initial()
      )
    }
  }
  data class ToLoading(
    private val articles: List<Article>
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        article = AppState.ArticleState.Loading(
          articles = articles,
          after = state.article.after
        )
      )
    }
  }
  data class ToIdeal(
    private val articles: List<Article>,
    private val after: String?
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        article = AppState.ArticleState.Ideal(
          articles = state.article.articles.plus(articles),
          after = after
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
  data class Update(
    private val newArticle: Article
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        article = AppState.ArticleState.Ideal(
          articles = state.article.articles.map { oldArticle ->
            if (oldArticle.id == newArticle.id) {
              newArticle
            } else {
              oldArticle
            }
          },
          after = state.article.after
        )
      )
    }
  }
}

sealed class CommunityAction : AppAction() {
  data class ToLoading(
    private val communities: List<Community.Detail>,
  ) : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        community = AppState.CommunityState.Loading(
          communities = communities,
          after = state.community.after
        )
      )
    }
  }
  data class ToIdeal(
    private val communities: List<Community.Detail>,
    private val after: String?
  ) : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        community = AppState.CommunityState.Ideal(
          communities = state.community.communities.plus(communities),
          after = after
        )
      )
    }
  }
  object ToError : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        community = AppState.CommunityState.Error()
      )
    }
  }
  object DoNothing : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state
    }
  }
}
