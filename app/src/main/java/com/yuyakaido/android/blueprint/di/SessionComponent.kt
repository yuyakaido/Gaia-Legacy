package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.domain.Session
import dagger.Subcomponent

@SessionScope
@Subcomponent(modules = arrayOf(SessionModule::class))
interface SessionComponent {
    fun inject(session: Session)
}