package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.SessionState

class RunningSession {

  private val sessions = mutableMapOf<String, SessionComponent>()

  fun add(
    state: SessionState,
    component: AppComponent
  ) {
    if (!sessions.containsKey(state.id())) {
      sessions[state.id()] = component.newSessionComponent().build()
    }
  }

  fun component(state: SessionState): SessionComponent {
    return sessions.getValue(state.id())
  }

}