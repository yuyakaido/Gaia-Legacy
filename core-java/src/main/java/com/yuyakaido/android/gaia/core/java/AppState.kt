package com.yuyakaido.android.gaia.core.java

data class AppState(
    val index: Int = 0,
    val sessions: List<Session> = emptyList()
) : StateType