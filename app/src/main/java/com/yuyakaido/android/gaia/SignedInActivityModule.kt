package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.support.SessionListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn

@InstallIn(SignedInComponent::class)
@Module
abstract class SignedInActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeSessionListActivity(): SessionListActivity

}