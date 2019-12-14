package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.app.AppRouterType
import com.yuyakaido.android.gaia.core.app.AppScope
import com.yuyakaido.android.gaia.core.app.NoopAppRouter
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppRouterType(
    application: Application
  ): AppRouterType {
    return NoopAppRouter(application = application)
  }

}