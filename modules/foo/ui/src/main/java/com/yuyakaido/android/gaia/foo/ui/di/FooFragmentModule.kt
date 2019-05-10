package com.yuyakaido.android.gaia.foo.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.foo.ui.FooFragment
import com.yuyakaido.android.gaia.foo.ui.FooViewModel
import com.yuyakaido.android.gaia.foo.ui.FooViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FooFragmentModule {

    @Provides
    fun provideFooViewModel(
        fragment: FooFragment,
        factory: FooViewModelFactory
    ): FooViewModel {
        return ViewModelProviders
            .of(fragment, factory)
            .get(FooViewModel::class.java)
    }

}