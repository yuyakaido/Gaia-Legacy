package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModuleType
import com.yuyakaido.android.gaia.article.list.ArticleListViewModelType
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import dagger.Module
import dagger.Provides

@Module
class ArticleListStorybookModule : ArticleListModuleType {

  @Provides
  override fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    service: RedditAuthService
  ): ArticleListViewModelType {
    return ArticleListStorybookViewModel(
      application = application,
      page = fragment.getArticleListPage(),
      service = service
    )
  }

}