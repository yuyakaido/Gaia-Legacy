package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchViewModel(
  application: Application
) : AndroidViewModel(
  application
) {

  private val service = getApplication<GaiaType>().redditPublicService

  val trendingArticles = MutableLiveData<List<TrendingArticle>>()
  val searchedArticles = MutableLiveData<List<Article>>()

  fun onBind() {
    service
      .trending()
      .enqueue(object : Callback<TrendingResponse> {
        override fun onResponse(call: Call<TrendingResponse>, response: Response<TrendingResponse>) {
          response.body()?.let { body ->
            trendingArticles.postValue(body.toEntities())
          }
        }
        override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

  fun onTextChange(text: String) {
    service
      .search(query = text)
      .enqueue(object : Callback<ListingDataResponse> {
        override fun onResponse(call: Call<ListingDataResponse>, response: Response<ListingDataResponse>) {
          Timber.d(response.toString())
        }
        override fun onFailure(call: Call<ListingDataResponse>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

}