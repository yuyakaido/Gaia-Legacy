package com.yuyakaido.android.gaia.core.java

data class AppState(
    val sessions: List<Session> = emptyList()
) : StateType