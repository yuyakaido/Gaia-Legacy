package com.yuyakaido.android.gaia.search

import android.app.Application
import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.repository.SearchRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedInComponent::class)
@Module
class SignedInSearchModule : MainSearchModule() {

  @SignedInScope
  @Provides
  fun provideSearchRepositoryType(
    application: Application,
    @RetrofitForPublic retrofit: Retrofit
  ): SearchRepositoryType {
    return createSearchRepositoryType(
      application = application,
      retrofit
    )
  }

}