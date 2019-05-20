package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.java.Session
import com.yuyakaido.android.gaia.di.SessionComponent

data class RunningSession(
    val components: MutableMap<Session, SessionComponent> = mutableMapOf()
)