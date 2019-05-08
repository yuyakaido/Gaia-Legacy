package com.yuyakaido.android.gaia.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.ui.MainFragment
import com.yuyakaido.android.gaia.ui.MainViewModel
import com.yuyakaido.android.gaia.ui.MainViewModelFactory
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