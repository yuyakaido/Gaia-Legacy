package com.yuyakaido.android.gaia.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import dagger.Module
import dagger.Provides

@Module
class ArticleListModule : ArticleListModuleType {

  @Provides
  override fun provideArticleListViewModel(
    application: Application,
    fragment: ArticleListFragment,
    repository: ArticleRepositoryType
  ): ArticleListViewModelType {
    return ArticleListViewModel(
      application = application,
      source = fragment.args.source,
      repository = repository
    )
  }

}