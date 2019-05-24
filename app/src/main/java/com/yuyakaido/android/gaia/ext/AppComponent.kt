package com.yuyakaido.android.gaia.ext

import com.yuyakaido.android.gaia.core.java.Session
import com.yuyakaido.android.gaia.di.AppComponent
import com.yuyakaido.android.gaia.di.SessionComponent
import com.yuyakaido.android.gaia.di.SessionModule

fun AppComponent.newSessionComponent(
    session: Session
): SessionComponent {
    return sessionComponentBuilder()
        .sessionModule(SessionModule(session))
        .build()
}