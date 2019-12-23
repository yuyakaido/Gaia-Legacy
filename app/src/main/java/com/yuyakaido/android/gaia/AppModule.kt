package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.auth.AuthTokenService
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.app.AuthTokenServiceType
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppRouterType(
    application: Application
  ): AppRouterType {
    return AppRouter(application = application)
  }

  @AppScope
  @Provides
  fun provideAuthTokenServiceType(
    application: Application
  ): AuthTokenServiceType {
    return AuthTokenService(application = application)
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

}