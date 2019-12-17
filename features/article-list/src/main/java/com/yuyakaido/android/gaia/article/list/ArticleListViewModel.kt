package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.value.ArticleListPage
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository
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
    vote(target = VoteTarget.forUpvote(article = article))
  }

  override fun onDownvote(article: Article) {
    vote(target = VoteTarget.forDownvote(article = article))
  }

  private fun vote(target: VoteTarget) {
    viewModelScope.launch {
      repository.vote(target = target)
      refresh(target)
    }
  }

}