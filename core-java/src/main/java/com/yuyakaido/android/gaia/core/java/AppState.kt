package com.yuyakaido.android.gaia.core.java

data class AppState(
    val lifecycle: AppLifecycle = AppLifecycle.OnAny,
    val index: Int = 0,
    val sessions: List<SessionState> = emptyList()
) : StateType