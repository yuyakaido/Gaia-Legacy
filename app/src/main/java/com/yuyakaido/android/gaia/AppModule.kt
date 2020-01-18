package com.yuyakaido.android.gaia

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.auth.TokenRepository
import com.yuyakaido.android.gaia.comment.CommentRepository
import com.yuyakaido.android.gaia.community.CommunityRepository
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.app.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.local.AppDatabase
import com.yuyakaido.android.gaia.core.infrastructure.remote.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.remote.PublicApi
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
    return AppRouter(
      application = application
    )
  }

  @AppScope
  @Provides
  fun provideAppDatabase(
    application: Application
  ): AppDatabase {
    return Room
      .databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase::class.java.simpleName
      )
      .build()
  }

  @AppScope
  @Provides
  fun provideTokenRepositoryType(
    application: Application,
    api: PublicApi
  ): TokenRepositoryType {
    return TokenRepository(
      application = application,
      api = api
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
  fun provideCommentRepositoryType(
    api: PrivateApi
  ): CommentRepositoryType {
    return CommentRepository(
      api = api
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
    api: PrivateApi,
    database: AppDatabase
  ): UserRepositoryType {
    return UserRepository(
      api = api,
      database = database
    )
  }

}