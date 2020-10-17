package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.AuthorizationActivity
import com.yuyakaido.android.gaia.auth.AuthorizationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignedOutActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeLauncherActivity(): LauncherActivity

  @ContributesAndroidInjector(
    modules = [AuthorizationModule::class]
  )
  abstract fun contributeAuthorizationActivity(): AuthorizationActivity

}