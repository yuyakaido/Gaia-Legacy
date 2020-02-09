package com.yuyakaido.android.gaia.search

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SearchModule {

  @AppScope
  @Provides
  fun provideSearchApi(
    @RetrofitForPublic retrofit: Retrofit
  ): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }

  @AppScope
  @Provides
  fun provideSearchRepositoryType(
    api: SearchApi
  ): SearchRepositoryType {
    return SearchRepository(
      api = api
    )
  }

}