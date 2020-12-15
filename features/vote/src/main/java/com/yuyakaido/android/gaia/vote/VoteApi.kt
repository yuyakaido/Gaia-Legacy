package com.yuyakaido.android.gaia.vote

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface VoteApi {

  @FormUrlEncoded
  @POST("api/vote")
  suspend fun vote(
    @Field("id") id: String,
    @Field("dir") dir: Int
  )

}