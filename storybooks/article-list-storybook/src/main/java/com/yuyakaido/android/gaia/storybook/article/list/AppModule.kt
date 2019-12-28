package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.community.CommunityRepository
import com.yuyakaido.android.gaia.core.domain.app.*
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import com.yuyakaido.android.gaia.user.UserRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppRouterType(
    application: Application
  ): AppRouterType {
    return NoopAppRouter(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideAuthTokenServiceType(
    application: Application
  ): AuthTokenServiceType {
    return NoopAuthTokenService(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideArticleRepositoryType(
    privateApi: PrivateApi,
    publicApi: PublicApi
  ): ArticleRepositoryType {
    return ArticleRepository(
      privateApi = privateApi,
      publicApi = publicApi
    )
  }

  @AppScope
  @Provides
  fun provideCommunityRepositoryType(
    api: PrivateApi
  ): CommunityRepositoryType {
    return CommunityRepository(
      api = api
    )
  }

  @AppScope
  @Provides
  fun provideUserRepositoryType(
    api: PrivateApi
  ): UserRepositoryType {
    return UserRepository(
      api = api
    )
  }

}