package com.yuyakaido.android.gaia.core.infrastructure.remote.api

import com.yuyakaido.android.gaia.core.infrastructure.remote.response.ListingDataResponse
import com.yuyakaido.android.gaia.core.infrastructure.remote.response.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicApi {

  @GET("api/trending_subreddits.json")
  suspend fun trending(): TrendingResponse

  @GET("search.json")
  suspend fun search(
    @Query("q") query: String
  ): ListingDataResponse

}