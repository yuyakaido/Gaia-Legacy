package com.yuyakaido.android.gaia

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.article.ArticleApi
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.auth.AuthApi
import com.yuyakaido.android.gaia.auth.TokenRepository
import com.yuyakaido.android.gaia.comment.CommentApi
import com.yuyakaido.android.gaia.comment.CommentRepository
import com.yuyakaido.android.gaia.community.CommunityApi
import com.yuyakaido.android.gaia.community.CommunityRepository
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.*
import com.yuyakaido.android.gaia.search.SearchApi
import com.yuyakaido.android.gaia.search.SearchRepository
import com.yuyakaido.android.gaia.user.infrastructure.local.MeDatabase
import com.yuyakaido.android.gaia.user.infrastructure.remote.UserApi
import com.yuyakaido.android.gaia.user.infrastructure.repository.UserRepository
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
  ): MeDatabase {
    return Room
      .databaseBuilder(
        application,
        MeDatabase::class.java,
        MeDatabase::class.java.simpleName
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
    api: ArticleApi
  ): ArticleRepositoryType {
    return ArticleRepository(
      api = api
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
    api: UserApi,
    database: MeDatabase
  ): UserRepositoryType {
    return UserRepository(
      api = api,
      database = database
    )
  }

  @AppScope
  @Provides
  fun provideSearchRepositoryType(
    api: SearchApi
  ): SearchRepositoryType {
    return SearchRepository(
      api = api
    )
  }

}