package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.home.HomeActivity
import com.yuyakaido.android.gaia.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun contributeHomeActivity(): HomeActivity

}