package com.yuyakaido.android.gaia.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleRepository

interface ArticleListModuleType {

  fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    repository: ArticleRepository
  ): ArticleListViewModelType

}