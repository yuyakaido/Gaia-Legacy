package com.yuyakaido.android.gaia.article.list

import dagger.Module
import dagger.Provides

@Module
class ArticleListModule {

  @Provides
  fun provideArticleListPage(
    fragment: ArticleListFragment
  ): ArticleListPage {
    return fragment.getArticleListPage()
  }

}