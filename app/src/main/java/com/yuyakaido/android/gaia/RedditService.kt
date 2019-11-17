package com.yuyakaido.android.gaia

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {
  @GET("subreddits/{path}")
  fun subreddits(
    @Path("path") path: String
  ): Call<SubredditListResponse>
}