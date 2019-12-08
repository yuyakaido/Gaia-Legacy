package com.yuyakaido.android.gaia.article.detail

import com.yuyakaido.android.gaia.core.entity.Article
import dagger.Module
import dagger.Provides

@Module
class ArticleDetailModule {

  @Provides
  fun provideArticle(
    activity: ArticleDetailActivity
  ): Article {
    return activity.getArticle()
  }

}