package com.yuyakaido.android.gaia.article.detail

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class ArticleDetailModule {

  @Provides
  fun provideArticleDetailViewModel(
    activity: ArticleDetailActivity,
    factory: ArticleDetailViewModelFactory
  ): ArticleDetailViewModel {
    return ViewModelProviders
      .of(activity, factory)
      .get(ArticleDetailViewModel::class.java)
  }

}