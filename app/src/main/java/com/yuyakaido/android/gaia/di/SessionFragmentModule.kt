package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.repo.ui.RepoFragment
import com.yuyakaido.android.gaia.repo.ui.di.RepoFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SessionFragmentModule {

    @ContributesAndroidInjector(modules = [RepoFragmentModule::class])
    abstract fun contributeRepoFragment(): RepoFragment

}