package com.yuyakaido.android.gaia.bar.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.bar.ui.BarActivity
import com.yuyakaido.android.gaia.bar.ui.BarViewModel
import com.yuyakaido.android.gaia.bar.ui.BarViewModelFactory
import com.yuyakaido.android.gaia.core.java.Repo
import dagger.Module
import dagger.Provides

@Module
class BarActivityModule {

    @Provides
    fun provideRepo(activity: BarActivity): Repo {
        return activity.getRepo()
    }

    @Provides
    fun provideFooViewModel(
        activity: BarActivity,
        factory: BarViewModelFactory
    ): BarViewModel {
        return ViewModelProviders
            .of(activity, factory)
            .get(BarViewModel::class.java)
    }

}