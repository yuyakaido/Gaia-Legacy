package com.yuyakaido.android.gaia.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.MainFragment
import com.yuyakaido.android.gaia.MainViewModel
import com.yuyakaido.android.gaia.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {

    @Provides
    fun provideQuery(fragment: MainFragment): String {
        return fragment.getQuery()
    }

    @Provides
    fun provideMainViewModel(
        fragment: MainFragment,
        factory: MainViewModelFactory
    ): MainViewModel {
        return ViewModelProviders
            .of(fragment, factory)
            .get(MainViewModel::class.java)
    }

}