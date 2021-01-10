package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.AuthorizationActivity
import com.yuyakaido.android.gaia.auth.AuthorizationModule
import com.yuyakaido.android.gaia.core.SignedOutComponent
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn

@InstallIn(SignedOutComponent::class)
@Module
abstract class SignedOutActivityModule {

  @ContributesAndroidInjector(
    modules = [AuthorizationModule::class]
  )
  abstract fun contributeAuthorizationActivity(): AuthorizationActivity

}