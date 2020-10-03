package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.StateType

data class AppState(
  val lifecycle: AppLifecycle = AppLifecycle.OnAny,
  val index: Int = 0,
  val sessions: List<SessionState> = emptyList()
) : StateType {

  val session get() = sessions[index]
  val signedIn get() = session as SessionState.SignedIn

  fun update(state: SessionState.SignedIn): AppState {
    return copy(
      sessions = listOf(state)
    )
  }

}