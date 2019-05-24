package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.ui.SelectEnvironmentDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppDialogModule {

    @ContributesAndroidInjector
    abstract fun contributeSelectEnvironmentDialog(): SelectEnvironmentDialog

}