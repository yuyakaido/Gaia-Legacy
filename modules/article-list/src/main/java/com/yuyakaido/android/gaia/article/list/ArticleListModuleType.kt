package com.yuyakaido.android.gaia.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService

interface ArticleListModuleType {

  fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    service: RedditAuthService
  ): ArticleListViewModelType

}