package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.infrastructure.ListingDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

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

}