package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.*
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository
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

  @AppScope
  @Provides
  fun provideAccessTokenServiceType(
    application: Application
  ): AccessTokenServiceType {
    return NoopAccessTokenService(application = application)
  }

  @AppScope
  @Provides
  fun provideVoteServiceType(
    repository: ArticleRepository
  ): VoteServiceType {
    return NoopVoteService()
  }

}