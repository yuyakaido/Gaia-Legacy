package com.yuyakaido.android.gaia.di

import android.app.Activity
import com.yuyakaido.android.gaia.auth.infra.AuthClientModule
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SessionScope
@Subcomponent(modules = [
    SessionModule::class,
    ClientModule::class,
    AuthClientModule::class,
    RepositoryModule::class,
    SessionActivityModule::class,
    SessionFragmentModule::class
])
interface SessionComponent {
    @Subcomponent.Builder
    interface Builder {
        fun sessionModule(sessionModule: SessionModule): Builder
        fun build(): SessionComponent
    }
    fun activityInjector(): DispatchingAndroidInjector<Activity>
}