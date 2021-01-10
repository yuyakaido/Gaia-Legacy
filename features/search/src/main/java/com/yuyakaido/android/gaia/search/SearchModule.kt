package com.yuyakaido.android.gaia.search

import android.app.Application
import com.yuyakaido.android.gaia.core.ComponentHandler
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class SearchModule : MainSearchModule() {

  @ActivityScoped
  @Provides
  fun provideSearchRepositoryType(
    application: Application,
    componentHandler: ComponentHandler
  ): SearchRepositoryType {
    return createSearchRepositoryType(
      application = application,
      retrofit = componentHandler.activeSignedInRetrofitForPublic()
    )
  }

}