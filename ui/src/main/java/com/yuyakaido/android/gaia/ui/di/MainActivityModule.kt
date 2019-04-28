package com.yuyakaido.android.gaia.ui.di

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.ui.MainActivity
import com.yuyakaido.android.gaia.ui.MainViewModel
import com.yuyakaido.android.gaia.ui.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindsActivity(activity: MainActivity): Activity

    @Module
    companion object {
        @JvmStatic
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

}