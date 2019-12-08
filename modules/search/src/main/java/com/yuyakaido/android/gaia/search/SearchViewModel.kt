package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.infrastructure.RedditPublicService
import com.yuyakaido.android.gaia.core.value.TrendingArticle
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  application: Application,
  private val service: RedditPublicService
) : AndroidViewModel(application) {

  val trendingArticles = MutableLiveData<List<TrendingArticle>>()
  val searchedArticles = MutableLiveData<List<Article>>()

  fun onBind() {
    viewModelScope.launch {
      val response = service.trending()
      trendingArticles.postValue(response.toEntities())
    }
  }

  fun onTextChange(text: String) {
    viewModelScope.launch {
      val response = service.search(query = text)
      val item = response.toArticlePaginationItem()
      searchedArticles.postValue(item.entities)
    }
  }

}