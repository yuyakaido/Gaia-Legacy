package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.app.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Module
@FlowPreview
@ExperimentalCoroutinesApi
class AppModule {

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
  fun provideAppStore(): AppStore {
    return AppStore()
  }

}