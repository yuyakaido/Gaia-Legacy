package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.auth.ui.CompleteAuthorizationActivity
import com.yuyakaido.android.gaia.auth.ui.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.bar.ui.BarActivity
import com.yuyakaido.android.gaia.bar.ui.di.BarActivityModule
import com.yuyakaido.android.gaia.foo.ui.FooActivity
import com.yuyakaido.android.gaia.foo.ui.di.FooActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FooActivityModule::class])
    abstract fun contributeFooActivity(): FooActivity

    @ContributesAndroidInjector(modules = [BarActivityModule::class])
    abstract fun contributeBarActivity(): BarActivity

    @ContributesAndroidInjector
    abstract fun contributeLaunchAuthorizationActivity(): LaunchAuthorizationActivity

    @ContributesAndroidInjector
    abstract fun contributeCompleteAuthorizationActivity(): CompleteAuthorizationActivity

}