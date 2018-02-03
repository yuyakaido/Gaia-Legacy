package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.presentation.AccountListActivity
import com.yuyakaido.android.blueprint.presentation.MainActivity
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: AccountListActivity)

    fun newAccountComponent(module: AccountModule): AccountComponent
}