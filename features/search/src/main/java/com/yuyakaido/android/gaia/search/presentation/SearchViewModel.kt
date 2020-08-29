package com.yuyakaido.android.gaia.search.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  application: Application,
  private val repository: SearchRepositoryType
) : BaseViewModel(application) {

  val trendingArticles = MutableLiveData<List<TrendingArticle>>()
  val searchHistories = MutableLiveData<List<SearchHistory>>()
  val searchArticles = MutableLiveData<List<Article>>()

  override fun onCreate() {
    super.onCreate()
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