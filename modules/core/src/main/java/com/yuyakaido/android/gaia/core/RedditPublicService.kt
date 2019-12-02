package com.yuyakaido.android.gaia.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditPublicService {

  @GET("api/trending_subreddits.json")
  fun trending(): Call<TrendingResponse>

  @GET("search.json")
  fun search(
    @Query("q") query: String
  ): Call<ListingDataResponse>

}