package com.yuyakaido.android.gaia

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [AndroidSupportInjectionModule::class])
class MainAppModule {

  @Singleton
  @Provides
  fun provideAppNavigatorType(
    application: Application
  ): AppNavigatorType {
    return AppNavigator(
      application = application
    )
  }

  @Singleton
  @Provides
  fun provideAppStore(
    application: Application
  ): AppStore {
    return AppStore(
      application = application,
      initialState = AppState(),
    )
  }

  @Singleton
  @Provides
  fun provideComponentHandler(
    appStore: AppStore,
    signedOutBuilder: SignedOutComponent.Builder,
    signingInBuilder: SigningInComponent.Builder,
    signedInBuilder: SignedInComponent.Builder
  ): ComponentHandler {
    return ComponentHandler(
      appStore = appStore,
      signedOutBuilder = signedOutBuilder,
      signingInBuilder = signingInBuilder,
      signedInBuilder = signedInBuilder
    )
  }

  @Singleton
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

  @Singleton
  @Provides
  fun provideSessionRepositoryType(
    database: SessionDatabase
  ): SessionRepositoryType {
    return SessionRepository(
      database = database
    )
  }

}