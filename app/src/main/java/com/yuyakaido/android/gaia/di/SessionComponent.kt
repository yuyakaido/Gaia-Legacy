package com.yuyakaido.android.gaia.di

import android.app.Activity
import com.yuyakaido.android.gaia.auth.infra.di.AuthClientModule
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SessionScope
@Subcomponent(modules = [
    SessionModule::class,
    ClientModule::class,
    AuthClientModule::class,
    RepositoryModule::class,
    ActivityModule::class,
    FragmentModule::class,
    DialogModule::class
])
interface SessionComponent {
    @Subcomponent.Builder
    interface Builder {
        fun sessionModule(sessionModule: SessionModule): Builder
        fun build(): SessionComponent
    }
    val activityInjector: DispatchingAndroidInjector<Activity>
}