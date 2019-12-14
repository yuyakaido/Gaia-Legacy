package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.*

interface RedditAuthService {

  @GET("r/{path}.json")
  suspend fun articles(
    @Path("path") path: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/androiddev/comments/e4xhq3.json")
  suspend fun comments(): List<ListingDataResponse>

  @GET("api/v1/me")
  suspend fun me(): MeResponse

  @GET("user/{user}/{type}")
  suspend fun voted(
    @Path("user") user: String,
    @Path("type") type: String
  ): ListingDataResponse

  @FormUrlEncoded
  @POST("api/vote")
  suspend fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  )

}
