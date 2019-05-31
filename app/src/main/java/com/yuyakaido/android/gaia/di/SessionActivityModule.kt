package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.auth.ui.CompleteAuthorizationActivity
import com.yuyakaido.android.gaia.auth.ui.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.bar.ui.BarActivity
import com.yuyakaido.android.gaia.bar.ui.di.BarActivityModule
import com.yuyakaido.android.gaia.repo.ui.RepoActivity
import com.yuyakaido.android.gaia.repo.ui.di.RepoActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SessionActivityModule {

    @ContributesAndroidInjector(modules = [RepoActivityModule::class])
    abstract fun contributeRepoActivity(): RepoActivity

    @ContributesAndroidInjector(modules = [BarActivityModule::class])
    abstract fun contributeBarActivity(): BarActivity

    @ContributesAndroidInjector
    abstract fun contributeLaunchAuthorizationActivity(): LaunchAuthorizationActivity

    @ContributesAndroidInjector
    abstract fun contributeCompleteAuthorizationActivity(): CompleteAuthorizationActivity

}