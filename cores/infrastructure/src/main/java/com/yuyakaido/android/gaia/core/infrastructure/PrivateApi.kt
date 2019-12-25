package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.*

interface PrivateApi {

  @GET("r/{name}/about")
  suspend fun detail(
    @Path("name") name: String
  ): CommunityResponse

  @GET("r/{path}")
  suspend fun articles(
    @Path("path") path: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/{category}/comments/{id}")
  suspend fun comments(
    @Path("category") category: String,
    @Path("id") id: String
  ): List<ListingDataResponse>

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

  @FormUrlEncoded
  @POST("api/subscribe")
  suspend fun subscribe(
    @Field("action") action: String = "sub",
    @Field("sr_name") name: String
  )

  @FormUrlEncoded
  @POST("api/subscribe")
  suspend fun unsubscribe(
    @Field("action") action: String = "unsub",
    @Field("sr_name") name: String
  )

}
