package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
  application: Application,
  override val source: ArticleListSource,
  override val repository: ArticleRepositoryType
) : ArticleListViewModelType(application) {

  override val items = MutableLiveData<List<EntityPaginationItem<Article>>>()

  private var after: String? = null
  private var isLoading: Boolean = false

  override fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
    if (items.value == null) {
      onPaginate()
    }
  }

  override fun onPaginate() {
    if (isLoading) {
      return
    }
    viewModelScope.launch {
      isLoading = true
      val result = source.articles(repository = repository, after = after)
      val oldItems = items.value ?: emptyList()
      val newItems = oldItems.plus(result)
      items.value = newItems
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