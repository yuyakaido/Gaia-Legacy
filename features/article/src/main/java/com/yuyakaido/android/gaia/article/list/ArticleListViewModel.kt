package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.ArticleState
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
  application: Application,
  initialSource: ArticleListSource,
  private val appStore: AppStore,
  private val actionCreator: ArticleListActionCreator
) : BaseViewModel(application) {

  private val currentSource = MutableStateFlow(initialSource)
  val state = currentSource
    .flatMapLatest { source -> appStore.articleAsFlow(source) }
    .map { state -> State.from(state = state) }
    .asLiveData()

  sealed class State {
    abstract val articles: List<Article>
    abstract val progressVisibility: Boolean

    object Initial: State() {
      override val articles: List<Article> = emptyList()
      override val progressVisibility: Boolean = true
    }
    data class Loading(
      override val articles: List<Article>,
      override val progressVisibility: Boolean = true
    ) : State()
    data class Ideal(
      override val articles: List<Article>,
      override val progressVisibility: Boolean = false
    ) : State()
    object Error: State() {
      override val articles: List<Article> = emptyList()
      override val progressVisibility: Boolean = false
    }

    companion object {
      fun from(state: ArticleState.ArticleListState): State {
        return when (state) {
          is ArticleState.ArticleListState.Initial -> {
            Initial
          }
          is ArticleState.ArticleListState.Loading -> {
            Loading(articles = state.articles)
          }
          is ArticleState.ArticleListState.Ideal -> {
            Ideal(articles = state.articles)
          }
          is ArticleState.ArticleListState.Error -> {
            Error
          }
        }
      }
    }
  }

  override fun onCreate() {
    super.onCreate()
    Timber.v("AppStore = $appStore")
    paginate()
  }

  fun onPaginate() {
    paginate()
  }

  fun onRefresh() {
    refresh()
  }

  fun onRefresh(source: ArticleListSource) {
    refresh(source = source)
  }

  fun onUpvote(article: Article) {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.upvote(
        source = currentSource.value,
        article = article
      )
    )
  }

  fun onDownvote(article: Article) {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.downvote(
        source = currentSource.value,
        article = article
      )
    )
  }

  private fun paginate() {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.paginate(source = currentSource.value)
    )
  }

  private fun refresh() {
    appStore.dispatch(actionCreator.refresh(currentSource.value))
    paginate()
  }

  private fun refresh(source: ArticleListSource) {
    currentSource.value = source
    refresh()
  }

}