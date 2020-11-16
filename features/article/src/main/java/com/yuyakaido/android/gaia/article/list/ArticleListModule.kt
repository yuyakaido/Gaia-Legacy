package com.yuyakaido.android.gaia.article.list

import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import dagger.Module
import dagger.Provides

@Module
class ArticleListModule {

  @Provides
  fun provideArticleListSource(
    fragment: ArticleListFragment
  ): ArticleListSource {
    return fragment.args.source
  }

}