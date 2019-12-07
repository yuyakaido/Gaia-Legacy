package com.yuyakaido.android.gaia.article.list

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class ArticleListModule {

  @Provides
  fun provideArticleListViewModel(
    fragment: ArticleListFragment,
    factory: ArticleListViewModelFactory
  ): ArticleListViewModel {
    return ViewModelProviders
      .of(fragment, factory)
      .get(ArticleListViewModel::class.java)
  }

}