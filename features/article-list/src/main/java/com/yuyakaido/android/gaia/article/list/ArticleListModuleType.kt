package com.yuyakaido.android.gaia.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.VoteServiceType
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType

interface ArticleListModuleType {

  fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    repository: ArticleRepositoryType,
    service: VoteServiceType
  ): ArticleListViewModelType

}