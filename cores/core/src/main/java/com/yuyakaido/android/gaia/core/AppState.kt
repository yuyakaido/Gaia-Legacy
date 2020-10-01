package com.yuyakaido.android.gaia.core

data class AppState(
  val lifecycle: AppLifecycle = AppLifecycle.OnAny,
  val index: Int = 0,
  val sessions: MutableList<SessionState> = mutableListOf()
) : StateType {

  val session get() = sessions[index]

  init {
    sessions.add(SessionState())
  }

  fun update(state: SessionState): AppState {
    sessions[index] = state
    return this
  }

}