package com.yuyakaido.android.gaia.foo.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.foo.ui.MainActivity
import com.yuyakaido.android.gaia.foo.ui.MainViewModel
import com.yuyakaido.android.gaia.foo.ui.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideQuery(activity: MainActivity): String {
        return activity.getQuery()
    }

    @Provides
    fun provideMainViewModel(
        activity: MainActivity,
        factory: MainViewModelFactory
    ): MainViewModel {
        return ViewModelProviders
            .of(activity, factory)
            .get(MainViewModel::class.java)
    }

}