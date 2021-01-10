package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.ComponentHandler
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class NetworkModule : MainNetworkModule() {

  @ActivityScoped
  @Provides
  fun provideImageLoaderType(
    componentHandler: ComponentHandler
  ): ImageLoaderType {
    return componentHandler.activeImageLoader()
  }

}