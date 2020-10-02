package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.domain.app.SessionScope
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ArticleModule {

  @SessionScope
  @Provides
  fun provideArticleApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): ArticleApi {
    return retrofit.create(ArticleApi::class.java)
  }

  @SessionScope
  @Provides
  fun provideArticleRepositoryType(
    api: ArticleApi
  ): ArticleRepositoryType {
    return ArticleRepository(
      api = api
    )
  }

}