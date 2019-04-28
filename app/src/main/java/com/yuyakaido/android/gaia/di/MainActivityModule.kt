package com.yuyakaido.android.gaia.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.MainActivity
import com.yuyakaido.android.gaia.MainViewModel
import com.yuyakaido.android.gaia.MainViewModelFactory
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