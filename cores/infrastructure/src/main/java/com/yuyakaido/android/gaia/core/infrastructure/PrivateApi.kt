package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.*

interface PrivateApi {

  @GET("r/{community}/about")
  suspend fun detail(
    @Path("community") community: String
  ): CommunityResponse

  @GET("r/{community}/about/moderators")
  suspend fun moderators(
    @Path("community") community: String
  ): UserListResponse

  @GET("r/{community}/about/contributors")
  suspend fun contributors(
    @Path("community") community: String
  ): UserListResponse

  @GET("r/{community}")
  suspend fun articles(
    @Path("community") community: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/{community}/comments/{article}")
  suspend fun comments(
    @Path("community") community: String,
    @Path("article") article: String
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
