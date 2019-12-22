package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.POST
import retrofit2.http.Query

interface RedditWwwApi {

  @POST("api/v1/access_token")
  suspend fun getInitialToken(
    @Query("grant_type") grantType: String = "authorization_code",
    @Query("redirect_uri") redirectUri: String = "com.yuyakaido.android.gaia://complete_authorization",
    @Query("code") code: String
  ): AccessTokenResponse

  @POST("api/v1/access_token")
  suspend fun refreshToken(
    @Query("grant_type") grantType: String = "refresh_token",
    @Query("refresh_token") refreshToken: String
  ): AccessTokenResponse

}