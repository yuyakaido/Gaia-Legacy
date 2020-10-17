package com.yuyakaido.android.gaia.comment

import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommentModule {

  @SignedInScope
  @Provides
  fun provideCommentApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): CommentApi {
    return retrofit.create(CommentApi::class.java)
  }

  @SignedInScope
  @Provides
  fun provideCommentRepositoryType(
    api: CommentApi
  ): CommentRepositoryType {
    return CommentRepository(
      api = api
    )
  }

}