package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

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
      fun from(state: AppState.ArticleState): State {
        return when (state) {
          is AppState.ArticleState.Initial -> {
            Initial
          }
          is AppState.ArticleState.Loading -> {
            Loading(articles = state.articles)
          }
          is AppState.ArticleState.Ideal -> {
            Ideal(articles = state.articles)
          }
          is AppState.ArticleState.Error -> {
            Error
          }
        }
      }
    }
  }

  override fun onCreate() {
    super.onCreate()
    Timber.d("repository = ${repository.hashCode()}")
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
    vote(target = VoteTarget.forUpvote(entity = article))
  }

  fun onDownvote(article: Article) {
    vote(target = VoteTarget.forDownvote(entity = article))
  }

  private fun paginate() {
    source.value?.let { s ->
      appStore.dispatch(
        scope = viewModelScope,
        action = actionCreator.paginate(source = s)
      )
    }
  }

  private fun refresh() {
    appStore.dispatch(actionCreator.refresh())
    paginate()
  }

  private fun refresh(source: ArticleListSource) {
    this.source.value = source
    refresh()
  }

  private fun vote(target: VoteTarget) {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.vote(target = target)
    )
  }

}