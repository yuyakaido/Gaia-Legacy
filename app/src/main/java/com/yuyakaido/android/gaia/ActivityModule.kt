package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.AuthorizationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeAuthorizationActivity(): AuthorizationActivity

  @ContributesAndroidInjector
  abstract fun contributeAppActivity(): AppActivity

}