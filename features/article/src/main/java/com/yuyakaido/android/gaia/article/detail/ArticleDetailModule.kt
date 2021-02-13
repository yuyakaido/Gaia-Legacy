package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.entity.Article
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class ArticleDetailModule {

  @Provides
  internal fun provideArticle(
    fragment: ArticleDetailFragment
  ): Article {
    return fragment.args.article
  }

  @Provides
  internal fun provideArticleDetailStore(
    application: Application,
    article: Article
  ): ArticleDetailStore {
    return ArticleDetailStore(
      application = application,
      initialState = ArticleDetailState(article)
    )
  }

}