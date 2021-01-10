package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.search.infrastructure.HistoryDatabase
import com.yuyakaido.android.gaia.search.infrastructure.SearchApi
import com.yuyakaido.android.gaia.search.infrastructure.SearchRepository
import retrofit2.Retrofit

abstract class MainSearchModule {

  private fun createSearchApi(
    retrofit: Retrofit
  ): SearchApi {
    return retrofit.create(SearchApi::class.java)
  }

  private fun createHistoryDatabase(
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

  fun createSearchRepositoryType(
    application: Application,
    retrofit: Retrofit
  ): SearchRepositoryType {
    return SearchRepository(
      api = createSearchApi(
        retrofit = retrofit
      ),
      database = createHistoryDatabase(
        application = application
      )
    )
  }

}