package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.presentation.MainActivity
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(SessionModule::class))
interface SessionComponent {
    fun inject(activity: MainActivity)
}