package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.*

interface PrivateApi {

  @GET("r/{community}")
  suspend fun articlesOfCommunity(
    @Path("community") community: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/{community}/comments/{article}")
  suspend fun commentsOfArticle(
    @Path("community") community: String,
    @Path("article") article: String
  ): List<ListingDataResponse>

  @FormUrlEncoded
  @POST("api/vote")
  suspend fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  )

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

  @GET("api/v1/me")
  suspend fun me(): MeResponse

  @GET("user/{user}/about")
  suspend fun user(
    @Path("user") user: String
  ): UserResponse

  @GET("user/{user}/submitted")
  suspend fun articlesOfUser(
    @Path("user") user: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("user/{user}/comments")
  suspend fun commentsOfUser(
    @Path("user") user: String
  ): ListingDataResponse

  @GET("user/{user}/{type}")
  suspend fun voted(
    @Path("user") user: String,
    @Path("type") type: String,
    @Query("after") after: String?
  ): ListingDataResponse

}
