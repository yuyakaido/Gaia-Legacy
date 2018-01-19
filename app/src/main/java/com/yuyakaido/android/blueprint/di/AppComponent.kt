package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
}