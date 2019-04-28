package com.yuyakaido.android.gaia.ui.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.ui.MainFragment
import com.yuyakaido.android.gaia.ui.MainViewModel
import com.yuyakaido.android.gaia.ui.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainFragmentModule {

    @Binds
    abstract fun bindsFragment(fragment: MainFragment): Fragment

    @Module
    companion object {
        @JvmStatic
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

}