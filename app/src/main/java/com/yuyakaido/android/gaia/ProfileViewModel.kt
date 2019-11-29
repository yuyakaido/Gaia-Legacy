package com.yuyakaido.android.gaia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.core.AuthInterceptor
import com.yuyakaido.android.gaia.core.Me
import com.yuyakaido.android.gaia.core.MeResponse
import com.yuyakaido.android.gaia.core.RedditService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ProfileViewModel : ViewModel() {

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

  val me = MutableLiveData<Me>()

  fun onBind() {
    service
      .me()
      .enqueue(object : Callback<MeResponse> {
        override fun onResponse(call: Call<MeResponse>, response: Response<MeResponse>) {
          response.body()?.let { body ->
            me.postValue(body.toEntity())
          }
        }
        override fun onFailure(call: Call<MeResponse>, t: Throwable) {
          Log.d("Gaia", t.toString())
        }
      })
  }

}