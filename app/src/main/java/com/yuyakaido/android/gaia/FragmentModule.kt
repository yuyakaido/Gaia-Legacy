package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.profile.ProfileModule
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(modules = [ArticleListModule::class])
  abstract fun contributeArticleListFragment(): ArticleListFragment

  @ContributesAndroidInjector(modules = [ProfileModule::class])
  abstract fun contributeProfileFragment(): ProfileFragment

}