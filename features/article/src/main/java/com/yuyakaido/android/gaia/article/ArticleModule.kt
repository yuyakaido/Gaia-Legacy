package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ArticleModule {

  @AppScope
  @Provides
  fun provideArticleApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): ArticleApi {
    return retrofit.create(ArticleApi::class.java)
  }

}