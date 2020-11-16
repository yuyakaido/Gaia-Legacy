package com.yuyakaido.android.gaia.article.list

import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.ArticleAction
import com.yuyakaido.android.gaia.core.SuspendableAction
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.reduxkit.DispatcherType
import com.yuyakaido.android.reduxkit.SelectorType
import javax.inject.Inject

class ArticleListActionCreator @Inject constructor(
  private val repository: ArticleRepositoryType
) {

  fun refresh(source: ArticleListSource): AppAction {
    return ArticleAction.ToInitial(source)
  }

  fun paginate(source: ArticleListSource): SuspendableAction {
    return object : SuspendableAction {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ) {
        val state = selector.select().signedIn.article.find(source)
        dispatcher.dispatch(
          ArticleAction.ToLoading(
            source = source,
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
              source = source,
              after = item.after,
              articles = item.entities
            )
          )
        } catch (e: Exception) {
          dispatcher.dispatch(
            ArticleAction.ToError(source)
          )
        }
      }
    }
  }

  fun vote(
    source: ArticleListSource,
    target: VoteTarget
  ): SuspendableAction {
    return object : SuspendableAction {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ) {
        repository.vote(target)
        dispatcher.dispatch(
          ArticleAction.Update(
            source = source,
            newArticle = target.article()
          )
        )
      }
    }
  }

}