package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.detail.ArticleDetailModule
import com.yuyakaido.android.gaia.auth.AuthorizationActivity
import com.yuyakaido.android.gaia.community.detail.CommunityDetailActivity
import com.yuyakaido.android.gaia.community.detail.CommunityDetailModule
import com.yuyakaido.android.gaia.user.detail.UserDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeAuthorizationActivity(): AuthorizationActivity

  @ContributesAndroidInjector
  abstract fun contributeAppActivity(): AppActivity

  @ContributesAndroidInjector(
    modules = [ArticleDetailModule::class]
  )
  abstract fun contributeArticleDetailActivity(): ArticleDetailActivity

  @ContributesAndroidInjector(
    modules = [CommunityDetailModule::class]
  )
  abstract fun contributeCommunityDetailActivity(): CommunityDetailActivity

  @ContributesAndroidInjector
  abstract fun contributeUserDetailActivity(): UserDetailActivity

}