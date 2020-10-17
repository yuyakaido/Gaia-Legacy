package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.AuthorizationActivity
import com.yuyakaido.android.gaia.auth.AuthorizationModule
import com.yuyakaido.android.gaia.support.SessionListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeLauncherActivity(): LauncherActivity

  @ContributesAndroidInjector(
    modules = [AuthorizationModule::class]
  )
  abstract fun contributeAuthorizationActivity(): AuthorizationActivity

  @ContributesAndroidInjector
  abstract fun contributeAppActivity(): AppActivity

  @ContributesAndroidInjector
  abstract fun contributeSessionListActivity(): SessionListActivity

}