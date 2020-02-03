package com.yuyakaido.android.gaia.core.infrastructure.remote.api

import com.yuyakaido.android.gaia.core.infrastructure.remote.response.*
import retrofit2.http.*

interface PrivateApi {

  @GET("r/{community}/about/moderators")
  suspend fun moderators(
    @Path("community") community: String
  ): UserListResponse

  @GET("r/{community}/about/contributors")
  suspend fun contributors(
    @Path("community") community: String
  ): UserListResponse

  @GET("api/v1/me")
  suspend fun me(): MeResponse

  @GET("user/{user}/about")
  suspend fun user(
    @Path("user") user: String
  ): UserResponse

}
