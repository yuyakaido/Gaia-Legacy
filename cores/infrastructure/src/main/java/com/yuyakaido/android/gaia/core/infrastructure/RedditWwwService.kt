package com.yuyakaido.android.gaia.core.infrastructure

import retrofit2.http.POST
import retrofit2.http.Query

interface RedditWwwService {

  @POST("api/v1/access_token")
  suspend fun accessToken(
    @Query("grant_type") grantType: String = "authorization_code",
    @Query("redirect_uri") redirectUri: String = "com.yuyakaido.android.gaia://complete_authorization",
    @Query("code") code: String
  ): AccessTokenResponse

}