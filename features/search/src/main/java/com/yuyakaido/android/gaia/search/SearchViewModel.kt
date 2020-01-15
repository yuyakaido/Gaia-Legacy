package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  application: Application,
  private val repository: ArticleRepositoryType
) : AndroidViewModel(application) {

  val trendingArticles = MutableLiveData<List<TrendingArticle>>()
  val searchedArticles = MutableLiveData<List<Article>>()

  fun onBind() {
    viewModelScope.launch {
      val articles = repository.trendingArticles()
      trendingArticles.value = articles
    }
  }

  fun onSubmit(query: String) {
    viewModelScope.launch {
      val result = repository.search(query = query)
      searchedArticles.value = result.entities
    }
  }

}