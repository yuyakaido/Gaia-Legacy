package com.yuyakaido.android.gaia.article.list

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ArticleListViewModel @Inject constructor(
  application: Application,
  source: ArticleListSource,
  private val appStore: AppStore,
  private val actionCreator: ArticleListActionCreator,
  private val repository: ArticleRepositoryType
) : BaseViewModel(application) {

  private val source = MutableLiveData(source)

  val state = appStore.articleAsFlow()
    .map { state -> State.from(state = state) }
    .asLiveData()

  // With paging library
  val itemsWithPaging = Pager(PagingConfig(10)) {
    ArticlePagingSource(
      source = source,
      repository = repository
    )
  }.flow

  sealed class State {
    abstract val progressVisibility: Int
    abstract val articles: List<Article>

    data class Initial(
      override val progressVisibility: Int = View.GONE,
      override val articles: List<Article>
    ) : State()
    data class Loading(
      override val progressVisibility: Int = View.VISIBLE,
      override val articles: List<Article>
    ) : State()
    data class  Error(
      override val progressVisibility: Int = View.GONE,
      override val articles: List<Article>
    ) : State()
    data class Ideal(
      override val progressVisibility: Int = View.GONE,
      override val articles: List<Article>
    ) : State()

    companion object {
      fun from(state: AppState.ArticleState): State {
        return when (state) {
          is AppState.ArticleState.Initial -> {
            Initial(articles = state.articles)
          }
          is AppState.ArticleState.Loading -> {
            Loading(articles = state.articles)
          }
          is AppState.ArticleState.Error -> {
            Error(articles = state.articles)
          }
          is AppState.ArticleState.Ideal -> {
            Ideal(articles = state.articles)
          }
        }
      }
    }
  }

  override fun onCreate() {
    super.onCreate()
    Timber.d("repository = ${repository.hashCode()}")
    onPaginate()
  }

  fun onRefresh(source: ArticleListSource) {
    this.source.value = source
    onPaginate()
  }

  fun onPaginate() {
    source.value?.let { s ->
      appStore.dispatch(
        scope = viewModelScope,
        action = actionCreator.fetch(source = s)
      )
    }
  }

  fun onUpvote(article: Article) {
    vote(target = VoteTarget.forUpvote(entity = article))
  }

  fun onDownvote(article: Article) {
    vote(target = VoteTarget.forDownvote(entity = article))
  }

  private fun vote(target: VoteTarget) {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.vote(target = target)
    )
  }

}