package com.yuyakaido.android.gaia.community

import com.yuyakaido.android.gaia.core.infrastructure.ListingDataResponse
import retrofit2.http.*

interface CommunityApi {

  @GET("subreddits/mine")
  suspend fun communitiesOfMine(
    @Query("after") after: String?
  ): ListingDataResponse

  @GET("r/{community}/about")
  suspend fun detail(
    @Path("community") community: String
  ): CommunityResponse

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