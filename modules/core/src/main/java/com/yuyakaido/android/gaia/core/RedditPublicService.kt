package com.yuyakaido.android.gaia.core

import retrofit2.Call
import retrofit2.http.GET

interface RedditPublicService {
  @GET("api/trending_subreddits.json")
  fun trending(): Call<TrendingResponse>
}