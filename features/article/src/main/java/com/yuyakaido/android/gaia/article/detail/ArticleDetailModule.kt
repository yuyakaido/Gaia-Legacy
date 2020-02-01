package com.yuyakaido.android.gaia.article.detail

import com.yuyakaido.android.gaia.core.domain.entity.Article
import dagger.Module
import dagger.Provides

@Module
class ArticleDetailModule {

  @Provides
  fun provideArticle(
    fragment: ArticleDetailFragment
  ): Article {
    return fragment.args.article
  }

}