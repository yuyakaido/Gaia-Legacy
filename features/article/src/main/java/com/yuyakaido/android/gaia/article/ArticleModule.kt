package com.yuyakaido.android.gaia.article

import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedInComponent::class)
@Module
class ArticleModule {

  @SignedInScope
  @Provides
  fun provideArticleApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): ArticleApi {
    return retrofit.create(ArticleApi::class.java)
  }

  @SignedInScope
  @Provides
  fun provideArticleRepositoryType(
    api: ArticleApi
  ): ArticleRepositoryType {
    return ArticleRepository(
      api = api
    )
  }

}