package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.Article
import com.yuyakaido.android.gaia.core.EntityPaginationItem
import com.yuyakaido.android.gaia.core.RedditAuthService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
  application: Application,
  private val page: ArticleListPage,
  private val service: RedditAuthService
) : AndroidViewModel(application) {

  val items = MutableLiveData<List<EntityPaginationItem<Article>>>()
  var after: String? = null
  var isLoading: Boolean = false

  fun onBind() {
    Timber.d("service = ${service.hashCode()}")
    if (items.value == null) {
      onPaginate(page)
    }
  }

  fun onPaginate(page: ArticleListPage) {
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

  fun onUpvote(article: Article) {
    val pair: Pair<Int, Boolean?> = when {
      article.likes == null -> 1 to true
      article.likes == true -> 0 to null
      article.likes == false -> 1 to true
      else -> 0 to null
    }
    vote(article = article, pair = pair)
  }

  fun onDownvote(article: Article) {
    val pair: Pair<Int, Boolean?> = when {
      article.likes == null -> -1 to false
      article.likes == true -> -1 to false
      article.likes == false -> 0 to null
      else -> 0 to null
    }
    vote(article = article, pair = pair)
  }

  private fun vote(article: Article, pair: Pair<Int, Boolean?>) {
    viewModelScope.launch {
      service.vote(id = article.name, dir = pair.first)
      val newItems = items
        .value
        ?.map { item ->
          item.copy(
            entities = item
              .entities
              .map { entity ->
                if (entity.id == article.id) {
                  entity.copy(likes = pair.second)
                } else {
                  entity
                }
              }
          )
        }
      items.postValue(newItems)
    }
  }

}