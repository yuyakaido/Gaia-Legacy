package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import com.yuyakaido.android.gaia.core.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.value.VoteResult
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
  application: Application,
  override val page: ArticleListPage,
  override val service: RedditAuthService
) : ArticleListViewModelType(application) {

  override val items = MutableLiveData<List<EntityPaginationItem<Article>>>()

  var after: String? = null
  var isLoading: Boolean = false

  override fun onBind() {
    Timber.d("service = ${service.hashCode()}")
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
      val response = service.articles(path = page.path, after = after)
      val oldItems = items.value ?: emptyList()
      val newItems = oldItems.plus(response.toArticlePaginationItem())
      items.postValue(newItems)
      after = response.data.after
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
      service.vote(id = result.article.name, dir = result.dir)
      refreshByVoteResult(result)
    }
  }

}