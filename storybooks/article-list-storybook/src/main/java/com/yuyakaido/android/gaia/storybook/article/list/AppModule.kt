package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.core.domain.app.*
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import com.yuyakaido.android.gaia.core.infrastructure.RedditPublicService
import com.yuyakaido.android.gaia.vote.VoteService
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
  fun provideArticleRepositoryType(
    authService: RedditAuthService,
    publicService: RedditPublicService
  ): ArticleRepositoryType {
    return ArticleRepository(
      authService = authService,
      publicService = publicService
    )
  }

  @AppScope
  @Provides
  fun provideVoteServiceType(
    repository: ArticleRepositoryType
  ): VoteServiceType {
    return VoteService(repository = repository)
  }

}