package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.foo.ui.FooFragment
import com.yuyakaido.android.gaia.foo.ui.di.FooFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [FooFragmentModule::class])
    abstract fun contributeFooFragment(): FooFragment

}