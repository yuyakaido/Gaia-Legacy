package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.infrastructure.remote.response.ListingDataResponse
import retrofit2.http.*

interface CommentApi {

  @GET("r/{community}/comments/{article}")
  suspend fun commentsOfArticle(
    @Path("community") community: String,
    @Path("article") article: String
  ): List<ListingDataResponse>

  @GET("user/{user}/comments")
  suspend fun commentsOfUser(
    @Path("user") user: String
  ): ListingDataResponse

  @FormUrlEncoded
  @POST("api/vote")
  suspend fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  )

}