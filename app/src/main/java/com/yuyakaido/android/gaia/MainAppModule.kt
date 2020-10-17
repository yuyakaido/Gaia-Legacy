package com.yuyakaido.android.gaia

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import dagger.Module
import dagger.Provides

@Module
open class MainAppModule {

  @AppScope
  @Provides
  fun provideAppNavigatorType(
    application: Application
  ): AppNavigatorType {
    return AppNavigator(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideAppStore(
    application: Application
  ): AppStore {
    return AppStore(
      application = application,
      initialState = AppState()
    )
  }

  @AppScope
  @Provides
  fun provideRunningSession(): RunningSession {
    return RunningSession()
  }

  @AppScope
  @Provides
  fun provideSessionDatabase(
    application: Application
  ): SessionDatabase {
    return Room
      .databaseBuilder(
        application,
        SessionDatabase::class.java,
        SessionDatabase::class.java.simpleName
      )
      .build()
  }

  @AppScope
  @Provides
  fun provideSessionRepositoryType(
    database: SessionDatabase
  ): SessionRepositoryType {
    return SessionRepository(
      database = database
    )
  }

}