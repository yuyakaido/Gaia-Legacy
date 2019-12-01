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

  val trendingSubreddits = MutableLiveData<List<TrendingSubreddit>>()
  val searchedSubreddits = MutableLiveData<List<Subreddit>>()

  fun onBind() {
    service
      .trending()
      .enqueue(object : Callback<TrendingResponse> {
        override fun onResponse(call: Call<TrendingResponse>, response: Response<TrendingResponse>) {
          response.body()?.let { body ->
            trendingSubreddits.postValue(body.toEntities())
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
      .enqueue(object : Callback<SubredditListResponse> {
        override fun onResponse(call: Call<SubredditListResponse>, response: Response<SubredditListResponse>) {
          response.body()?.let { body ->
            searchedSubreddits.postValue(body.toEntities())
          }
        }
        override fun onFailure(call: Call<SubredditListResponse>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

}