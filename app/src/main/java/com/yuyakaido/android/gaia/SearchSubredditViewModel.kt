package com.yuyakaido.android.gaia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchSubredditViewModel : ViewModel() {

  val subreddits = MutableLiveData<List<Subreddit>>()

  fun onBind() {
    if (subreddits.value == null) {
      val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
      val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
      val service = retrofit.create(RedditService::class.java)

      service.search()
        .enqueue(object : Callback<SearchResult> {
          override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
            response.body()?.let { body ->
              subreddits.postValue(body.toEntities())
            }
          }
          override fun onFailure(call: Call<SearchResult>, t: Throwable) {
            Log.d("Gaia", t.toString())
          }
        })
    }
  }

}