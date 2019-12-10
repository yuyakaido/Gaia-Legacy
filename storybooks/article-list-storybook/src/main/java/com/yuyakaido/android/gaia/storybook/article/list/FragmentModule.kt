package com.yuyakaido.android.gaia.storybook.article.list

import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(
    modules = [ArticleListStorybookModule::class]
  )
  abstract fun contributeArticleListFragment(): ArticleListFragment

}