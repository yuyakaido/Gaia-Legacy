package com.yuyakaido.android.gaia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.core.Subreddit
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
      val interceptor = HttpLoggingInterceptor()
      interceptor.level = HttpLoggingInterceptor.Level.BASIC
      val client = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()
      val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
      val retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
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