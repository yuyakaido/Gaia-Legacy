package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModuleType
import com.yuyakaido.android.gaia.article.list.ArticleListViewModelType
import dagger.Module
import dagger.Provides

@Module
class ArticleListStorybookModule : ArticleListModuleType {

  @Provides
  override fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    repository: ArticleRepository
  ): ArticleListViewModelType {
    return ArticleListStorybookViewModel(
      application = application,
      page = fragment.getArticleListPage(),
      repository = repository
    )
  }

}