package com.yuyakaido.android.gaia.user

import com.yuyakaido.android.gaia.core.infrastructure.remote.response.MeResponse
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.UserListResponse
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

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