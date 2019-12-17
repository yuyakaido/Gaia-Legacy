package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModuleType
import com.yuyakaido.android.gaia.article.list.ArticleListViewModelType
import com.yuyakaido.android.gaia.core.domain.app.VoteServiceType
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import dagger.Module
import dagger.Provides

@Module
class ArticleListStorybookModule : ArticleListModuleType {

  @Provides
  override fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    repository: ArticleRepositoryType,
    service: VoteServiceType
  ): ArticleListViewModelType {
    return ArticleListStorybookViewModel(
      application = application,
      page = fragment.getArticleListPage(),
      repository = repository,
      service = service
    )
  }

}