package com.yuyakaido.android.gaia.core

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditPublicService {

  @GET("api/trending_subreddits.json")
  suspend fun trending(): TrendingResponse

  @GET("search.json")
  suspend fun search(
    @Query("q") query: String
  ): ListingDataResponse

}