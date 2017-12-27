package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}