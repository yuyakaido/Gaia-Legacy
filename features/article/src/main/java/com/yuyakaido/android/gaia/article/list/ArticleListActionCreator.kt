package com.yuyakaido.android.gaia.article.list

import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject

class ArticleListActionCreator @Inject constructor(
  private val repository: ArticleRepositoryType
) {

  fun fetch(source: ArticleListSource): SuspendableActionType<AppState> {
    return object : SuspendableActionType<AppState> {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): ActionType<AppState> {
        val state = selector.select().article
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

  fun vote(target: VoteTarget): SuspendableActionType<AppState> {
    return object : SuspendableActionType<AppState> {
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