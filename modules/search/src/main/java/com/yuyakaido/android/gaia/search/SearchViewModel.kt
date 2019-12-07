package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.Article
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.TrendingArticle
import kotlinx.coroutines.launch

class SearchViewModel(
  application: Application
) : AndroidViewModel(
  application
) {

  private val service = getApplication<GaiaType>().redditPublicService

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