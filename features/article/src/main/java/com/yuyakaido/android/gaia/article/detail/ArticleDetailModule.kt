package com.yuyakaido.android.gaia.article.detail

import com.yuyakaido.android.gaia.core.domain.entity.Article
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class ArticleDetailModule {

  @Provides
  fun provideArticle(
    fragment: ArticleDetailFragment
  ): Article {
    return fragment.args.article
  }

}