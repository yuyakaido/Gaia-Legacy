package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment

}