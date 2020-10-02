package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.domain.app.SessionScope
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommentModule {

  @SessionScope
  @Provides
  fun provideCommentApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): CommentApi {
    return retrofit.create(CommentApi::class.java)
  }

  @SessionScope
  @Provides
  fun provideCommentRepositoryType(
    api: CommentApi
  ): CommentRepositoryType {
    return CommentRepository(
      api = api
    )
  }

}