package com.yuyakaido.android.gaia.article.list

import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.ArticleAction
import com.yuyakaido.android.gaia.core.SuspendableAction
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.reduxkit.DispatcherType
import com.yuyakaido.android.reduxkit.SelectorType
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
      ) {
        val state = selector.select().signedIn.article
        dispatcher.dispatch(
          ArticleAction.ToLoading(
            articles = state.articles
          )
        )
        try {
          val item = source.articles(
            repository = repository,
            after = state.after
          )
          dispatcher.dispatch(
            ArticleAction.ToIdeal(
              after = item.after,
              articles = item.entities
            )
          )
        } catch (e: Exception) {
          dispatcher.dispatch(
            ArticleAction.ToError
          )
        }
      }
    }
  }

  fun vote(target: VoteTarget): SuspendableAction {
    return object : SuspendableAction {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ) {
        repository.vote(target)
        dispatcher.dispatch(
          ArticleAction.Update(
            newArticle = target.article()
          )
        )
      }
    }
  }

}