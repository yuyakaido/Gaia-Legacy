package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.support.SessionListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignedInActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeGatewayActivity(): GatewayActivity

  @ContributesAndroidInjector
  abstract fun contributeAppActivity(): AppActivity

  @ContributesAndroidInjector
  abstract fun contributeSessionListActivity(): SessionListActivity

}