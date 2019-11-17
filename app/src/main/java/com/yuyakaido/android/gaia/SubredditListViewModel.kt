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

class SubredditListViewModel : ViewModel() {

  val subreddits = MutableLiveData<List<Subreddit>>()

  fun onBind(page: HomePage) {
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

      service.subreddits(path = page.path)
        .enqueue(object : Callback<SubredditListResponse> {
          override fun onResponse(call: Call<SubredditListResponse>, response: Response<SubredditListResponse>) {
            response.body()?.let { body ->
              subreddits.postValue(body.toEntities())
            }
          }
          override fun onFailure(call: Call<SubredditListResponse>, t: Throwable) {
            Log.d("Gaia", t.toString())
          }
        })
    }
  }

}