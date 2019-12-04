package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.profile.ProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(modules = [ProfileModule::class])
  abstract fun contributeProfileFragment(): ProfileFragment

}