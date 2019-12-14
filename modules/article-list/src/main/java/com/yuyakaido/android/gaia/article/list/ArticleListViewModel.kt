package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository
import com.yuyakaido.android.gaia.core.value.ArticleListPage
import com.yuyakaido.android.gaia.core.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.value.VoteResult
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
  application: Application,
  override val page: ArticleListPage,
  override val repository: ArticleRepository
) : ArticleListViewModelType(application) {

  override val items = MutableLiveData<List<EntityPaginationItem<Article>>>()

  var after: String? = null
  var isLoading: Boolean = false

  override fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
    if (items.value == null) {
      onPaginate(page)
    }
  }

  override fun onPaginate(page: ArticleListPage) {
    if (isLoading) {
      return
    }
    viewModelScope.launch {
      isLoading = true
      val result = repository.articles(page = page, after = after)
      val oldItems = items.value ?: emptyList()
      val newItems = oldItems.plus(result)
      items.postValue(newItems)
      after = result.after
      isLoading = false
    }
  }

  override fun onUpvote(article: Article) {
    vote(result = VoteResult.forUpvote(article = article))
  }

  override fun onDownvote(article: Article) {
    vote(result = VoteResult.forDownvote(article = article))
  }

  private fun vote(result: VoteResult) {
    viewModelScope.launch {
      repository.vote(result = result)
      refreshByVoteResult(result)
    }
  }

}