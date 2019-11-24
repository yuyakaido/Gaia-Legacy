package com.yuyakaido.android.gaia

import retrofit2.Call
import retrofit2.http.*

interface RedditService {

  @GET("r/{path}.json")
  fun subreddits(
    @Path("path") path: String
  ): Call<SubredditListResponse>

  @FormUrlEncoded
  @POST("api/vote")
  fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  ): Call<Unit>

}
