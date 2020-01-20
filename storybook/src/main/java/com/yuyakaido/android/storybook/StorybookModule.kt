package com.yuyakaido.android.storybook

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.storybook.repository.ArticleRepositoryStoryBook
import com.yuyakaido.android.storybook.repository.CommentRepositoryStorybook
import dagger.Module
import dagger.Provides

@Module
class StorybookModule {

  @AppScope
  @Provides
  fun provideAppRouter(
    application: Application
  ): AppRouterType {
    return StorybookRouter(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideArticleRepository(): ArticleRepositoryType {
    return ArticleRepositoryStoryBook()
  }

  @AppScope
  @Provides
  fun provideCommentRepository(): CommentRepositoryType {
    return CommentRepositoryStorybook()
  }
}