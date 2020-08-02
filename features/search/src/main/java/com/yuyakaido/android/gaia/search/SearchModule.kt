package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.appcompat.widget.AppCompatImageButton
import androidx.room.Room
import com.yuyakaido.android.gaia.core.domain.app.AppScope
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

  @AppScope
  @Provides
  fun provideSearchApi(
    @RetrofitForPublic retrofit: Retrofit
  ): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }

  @AppScope
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

  @AppScope
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