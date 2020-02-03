package com.yuyakaido.android.gaia.core.infrastructure.remote.api

import com.yuyakaido.android.gaia.core.domain.app.Constant
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.AccessTokenResponse
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.ListingDataResponse
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PublicApi {

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

  @GET("api/trending_subreddits.json")
  suspend fun trending(): TrendingResponse

  @GET("search.json")
  suspend fun search(
    @Query("q") query: String
  ): ListingDataResponse

}