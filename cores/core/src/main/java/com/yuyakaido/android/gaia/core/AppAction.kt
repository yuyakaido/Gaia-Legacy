package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class AppAction : ActionType<AppState> {
  data class UpdateLifecycle(val lifecycle: AppLifecycle) : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        lifecycle = lifecycle
      )
    }
  }
}

sealed class SessionAction : AppAction()

interface SuspendableAction : SuspendableActionType<AppState>

interface SingleAction : SingleActionType<AppState>

sealed class ArticleAction : SessionAction() {
  object ToInitial : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          article = SessionState.ArticleState.Initial
        )
      )
    }
  }
  data class ToLoading(
    private val articles: List<Article>
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          article = SessionState.ArticleState.Loading(
            articles = articles,
            after = state.session.article.after
          )
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
        session = state.session.copy(
          article = SessionState.ArticleState.Ideal(
            articles = state.session.article.articles.plus(articles),
            after = after
          )
        )
      )
    }
  }
  object ToError : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          article = SessionState.ArticleState.Error
        )
      )
    }
  }
  data class Update(
    private val newArticle: Article
  ) : ArticleAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          article = SessionState.ArticleState.Ideal(
            articles = state.session.article.articles.map { oldArticle ->
              if (oldArticle.id == newArticle.id) {
                newArticle
              } else {
                oldArticle
              }
            },
            after = state.session.article.after
          )
        )
      )
    }
  }
}

sealed class CommunityAction : AppAction() {
  object ToInitial : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          community = SessionState.CommunityState.Initial
        )
      )
    }
  }
  data class ToLoading(
    private val communities: List<Community.Detail>,
  ) : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          community = SessionState.CommunityState.Loading(
            communities = communities,
            after = state.session.community.after
          )
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
        session = state.session.copy(
          community = SessionState.CommunityState.Ideal(
            communities = state.session.community.communities.plus(communities),
            after = after
          )
        )
      )
    }
  }
  object ToError : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        session = state.session.copy(
          community = SessionState.CommunityState.Error
        )
      )
    }
  }
  object DoNothing : CommunityAction() {
    override fun reduce(state: AppState): AppState {
      return state
    }
  }
}
