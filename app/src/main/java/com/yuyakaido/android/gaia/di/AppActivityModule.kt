package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.ui.SelectEnvironmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSelectEnvironmentActivity(): SelectEnvironmentActivity

}