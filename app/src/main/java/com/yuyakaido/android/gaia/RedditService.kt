package com.yuyakaido.android.gaia

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
  @GET("search.json")
  fun search(
    @Query("q") query: String = "android",
    @Query("sort") sort: String = "top"
  ): Call<SearchResult>
}