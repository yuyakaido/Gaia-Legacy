package com.yuyakaido.android.gaia.article.list

import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject

class ArticleListActionCreator @Inject constructor(
  private val repository: ArticleRepositoryType
) {

  fun refresh(): AppAction {
    return ArticleAction.ToInitial
  }

  fun paginate(source: ArticleListSource): SuspendableAction {
    return object : SuspendableAction {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): ActionType<AppState> {
        val state = selector.select().session.article
        dispatcher.dispatch(
          ArticleAction.ToLoading(
            articles = state.articles
          )
        )
        val item = source.articles(
          repository = repository,
          after = state.after
        )
        return ArticleAction.ToIdeal(
          after = item.after,
          articles = item.entities
        )
      }
    }
  }

  fun vote(target: VoteTarget): SuspendableAction {
    return object : SuspendableAction {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): ActionType<AppState> {
        repository.vote(target)
        return ArticleAction.Update(
          newArticle = target.article()
        )
      }
    }
  }

}