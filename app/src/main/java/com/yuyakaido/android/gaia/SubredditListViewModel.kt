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

  private val interceptor = HttpLoggingInterceptor()
    .apply { level = HttpLoggingInterceptor.Level.BASIC }
  private val client = OkHttpClient
    .Builder()
    .addInterceptor(interceptor)
    .addInterceptor(AuthInterceptor())
    .build()
  private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
  private val retrofit = Retrofit
    .Builder()
    .client(client)
    .baseUrl("https://oauth.reddit.com")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
  private val service = retrofit.create(RedditService::class.java)

  val subreddits = MutableLiveData<List<Subreddit>>()

  fun onBind(page: SubredditListPage) {
    if (subreddits.value == null) {
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

  fun onUpvote(subreddit: Subreddit) {
    val pair: Pair<Int, Boolean?> = when {
      subreddit.likes == null -> 1 to true
      subreddit.likes == true -> 0 to null
      subreddit.likes == false -> 1 to true
      else -> 0 to null
    }
    service.vote(id = subreddit.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newSubreddits = subreddits
            .value
            ?.map {
              if (it.id == subreddit.id) {
                subreddit.copy(likes = pair.second)
              } else {
                it
              }
            }
          subreddits.postValue(newSubreddits)
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
          Log.d("Gaia", t.toString())
        }
      })
  }

  fun onDownvote(subreddit: Subreddit) {
    val pair: Pair<Int, Boolean?> = when {
      subreddit.likes == null -> -1 to false
      subreddit.likes == true -> -1 to false
      subreddit.likes == false -> 0 to null
      else -> 0 to null
    }
    service.vote(id = subreddit.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newSubreddits = subreddits
            .value
            ?.map {
              if (it.id == subreddit.id) {
                subreddit.copy(likes = pair.second)
              } else {
                it
              }
            }
          subreddits.postValue(newSubreddits)
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
          Log.d("Gaia", t.toString())
        }
      })
  }

}