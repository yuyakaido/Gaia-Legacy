package com.yuyakaido.android.gaia.search.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  application: Application,
  private val repository: SearchRepositoryType
) : AndroidViewModel(application) {

  val trendingArticles = MutableLiveData<List<TrendingArticle>>()
  val searchHistories = MutableLiveData<List<SearchHistory>>()
  val searchArticles = MutableLiveData<List<Article>>()

  fun onBind() {
    viewModelScope.launch {
      val articles = repository.getTrendingArticles()
      trendingArticles.value = articles
    }
  }

  fun onFocus() {
    viewModelScope.launch {
      val histories = repository.getSearchHistories()
      searchHistories.value = histories
    }
  }

  fun onClose() {
    searchHistories.value = emptyList()
    searchArticles.value = emptyList()
  }

  fun onSubmit(query: String) {
    viewModelScope.launch {
      val item = repository.getSearchResult(query = query)
      val articles = item.entities.map { result -> result.article }
      searchArticles.value = articles
    }
  }

}