package com.yuyakaido.android.gaia.foo.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.foo.ui.FooActivity
import com.yuyakaido.android.gaia.foo.ui.FooViewModel
import com.yuyakaido.android.gaia.foo.ui.FooViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FooActivityModule {

    @Provides
    fun provideFooViewModel(
        activity: FooActivity,
        factory: FooViewModelFactory
    ): FooViewModel {
        return ViewModelProviders
            .of(activity, factory)
            .get(FooViewModel::class.java)
    }

}