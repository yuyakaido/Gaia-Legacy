package com.yuyakaido.android.gaia.core

import retrofit2.Call
import retrofit2.http.*

interface RedditAuthService {

  @GET("r/{path}.json")
  fun articles(
    @Path("path") path: String,
    @Query("after") after: String?
  ): Call<ListingDataResponse>

  @GET("r/androiddev/comments/e4xhq3.json")
  fun comments(): Call<List<ListingDataResponse>>

  @GET("api/v1/me")
  fun me(): Call<MeResponse>

  @FormUrlEncoded
  @POST("api/vote")
  fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  ): Call<Unit>

}
