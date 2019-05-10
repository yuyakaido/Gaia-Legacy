package com.yuyakaido.android.gaia.core

data class AppState(
    val sessions: List<Session> = emptyList()
) : StateType