package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.profile.ui.ProfileFragment
import com.yuyakaido.android.gaia.profile.ui.ProfileFragmentModule
import com.yuyakaido.android.gaia.repo.ui.RepoFragment
import com.yuyakaido.android.gaia.repo.ui.RepoFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SessionFragmentModule {

  @ContributesAndroidInjector(modules = [RepoFragmentModule::class])
  abstract fun contributeRepoFragment(): RepoFragment

  @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
  abstract fun contributeProfileFragment(): ProfileFragment

}