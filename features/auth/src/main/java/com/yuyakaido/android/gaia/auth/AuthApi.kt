package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.app.Constant
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

  @POST("api/v1/access_token")
  suspend fun getInitialToken(
    @Query("grant_type") grantType: String = Constant.OAUTH_GRANT_TYPE_FOR_ACCESS_TOKEN,
    @Query("redirect_uri") redirectUri: String = Constant.OAUTH_REDIRECT_URI,
    @Query("code") code: String
  ): AccessTokenResponse

  @POST("api/v1/access_token")
  suspend fun refreshToken(
    @Query("grant_type") grantType: String = Constant.OAUTH_GRANT_TYPE_FOR_REFRESH_TOKEN,
    @Query("refresh_token") refreshToken: String
  ): AccessTokenResponse

}