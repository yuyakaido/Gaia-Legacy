package com.yuyakaido.android.gaia

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.auth.AuthApi
import com.yuyakaido.android.gaia.auth.TokenRepository
import com.yuyakaido.android.gaia.comment.CommentApi
import com.yuyakaido.android.gaia.comment.CommentRepository
import com.yuyakaido.android.gaia.community.CommunityApi
import com.yuyakaido.android.gaia.community.CommunityRepository
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.*
import com.yuyakaido.android.gaia.core.infrastructure.local.AppDatabase
import com.yuyakaido.android.gaia.core.infrastructure.remote.api.PrivateApi
import com.yuyakaido.android.gaia.core.infrastructure.remote.api.PublicApi
import com.yuyakaido.android.gaia.core.infrastructure.repository.ArticleRepository
import com.yuyakaido.android.gaia.core.infrastructure.repository.UserRepository
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
    api: AuthApi
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
    api: CommentApi
  ): CommentRepositoryType {
    return CommentRepository(
      api = api
    )
  }

  @AppScope
  @Provides
  fun provideCommunityRepositoryType(
    api: CommunityApi
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