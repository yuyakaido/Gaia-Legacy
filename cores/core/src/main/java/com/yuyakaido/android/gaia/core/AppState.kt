package com.yuyakaido.android.gaia.core

data class AppState(
  val lifecycle: AppLifecycle = AppLifecycle.OnAny,
  val session: SessionState = SessionState()
) : StateType