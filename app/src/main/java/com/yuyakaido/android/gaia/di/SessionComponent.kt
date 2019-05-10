package com.yuyakaido.android.gaia.di

import android.app.Activity
import com.yuyakaido.android.gaia.foo.data.di.ClientModule
import com.yuyakaido.android.gaia.foo.data.di.RepositoryModule
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SessionScope
@Subcomponent(modules = [
    SessionModule::class,
    ClientModule::class,
    RepositoryModule::class,
    ActivityModule::class,
    FragmentModule::class
])
interface SessionComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SessionComponent
    }
    val activityInjector: DispatchingAndroidInjector<Activity>
}