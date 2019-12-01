package com.yuyakaido.android.gaia.core

import retrofit2.Call
import retrofit2.http.*

interface RedditAuthService {

  @GET("r/{path}.json")
  fun subreddits(
    @Path("path") path: String
  ): Call<SubredditListResponse>

  @GET("api/v1/me")
  fun me(): Call<MeResponse>

  @FormUrlEncoded
  @POST("api/vote")
  fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  ): Call<Unit>

}
