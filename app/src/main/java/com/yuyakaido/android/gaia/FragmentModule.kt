package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(
    modules = [ArticleListModule::class]
  )
  abstract fun contributeArticleListFragment(): ArticleListFragment

  @ContributesAndroidInjector
  abstract fun contributeSearchFragment(): SearchFragment

  @ContributesAndroidInjector
  abstract fun contributeProfileFragment(): ProfileFragment

}