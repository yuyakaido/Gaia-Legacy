package com.yuyakaido.android.storybooks.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.storybook.NoopAppRouter
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorybookModule {

  @AppScope
  @Provides
  fun provideAppRouter(
    application: Application
  ): AppRouterType {
    return com.yuyakaido.android.storybook.NoopAppRouter(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideArticleRepository(): ArticleRepositoryType {
    return ArticleRepositoryStoryBook()
  }
}