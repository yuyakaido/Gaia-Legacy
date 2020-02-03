package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.infrastructure.remote.response.ListingDataResponse
import retrofit2.http.*

interface ArticleApi {

  @GET("{sort}")
  suspend fun articlesOfSort(
    @Path("sort") sort: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/{community}")
  suspend fun articlesOfCommunity(
    @Path("community") community: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("user/{user}/submitted")
  suspend fun articlesOfUser(
    @Path("user") user: String,
    @Query("after") after: String?
  ): ListingDataResponse

  @FormUrlEncoded
  @POST("api/vote")
  suspend fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  )

  @GET("user/{user}/{type}")
  suspend fun voted(
    @Path("user") user: String,
    @Path("type") type: String,
    @Query("after") after: String?
  ): ListingDataResponse

}