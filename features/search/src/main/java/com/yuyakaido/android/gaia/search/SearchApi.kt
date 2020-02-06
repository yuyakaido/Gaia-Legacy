package com.yuyakaido.android.gaia.search

import com.yuyakaido.android.gaia.core.infrastructure.ListingDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

  @GET("api/trending_subreddits.json")
  suspend fun trendingArticles(): TrendingArticleResponse

  @GET("search.json")
  suspend fun search(
    @Query("q") query: String
  ): ListingDataResponse

}