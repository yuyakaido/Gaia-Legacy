package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.core.domain.app.SessionScope
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import com.yuyakaido.android.gaia.search.infrastructure.HistoryDatabase
import com.yuyakaido.android.gaia.search.infrastructure.SearchApi
import com.yuyakaido.android.gaia.search.infrastructure.SearchRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SearchModule {

  @SessionScope
  @Provides
  fun provideSearchApi(
    @RetrofitForPublic retrofit: Retrofit
  ): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }

  @SessionScope
  @Provides
  fun provideHistoryDatabase(
    application: Application
  ): HistoryDatabase {
    return Room
      .databaseBuilder(
        application,
        HistoryDatabase::class.java,
        HistoryDatabase::class.java.simpleName
      )
      .build()
  }

  @SessionScope
  @Provides
  fun provideSearchRepositoryType(
    api: SearchApi,
    database: HistoryDatabase
  ): SearchRepositoryType {
    return SearchRepository(
      api = api,
      database = database
    )
  }

}