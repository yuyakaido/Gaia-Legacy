package com.yuyakaido.android.storybooks.article.list

import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ArticleListFragmentModule {

  @ContributesAndroidInjector(
    modules = [ArticleListModule::class]
  )
  abstract fun contributeArticleListFragment(): ArticleListFragment

}