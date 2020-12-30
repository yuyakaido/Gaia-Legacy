package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.domain.entity.Session

class RunningSession {

  private val sessions = mutableMapOf<String, SignedInSessionComponent>()

  fun add(
    state: SessionState.SignedIn,
    component: AppComponent
  ) {
    if (!sessions.containsKey(state.id)) {
      val session = Session.SignedIn(
        id = state.id,
        token = state.token
      )
      sessions[state.id] = component
        .newSignedInSessionComponentFactory()
        .create(SignedInSessionModule(session))
    }
  }

  fun component(state: SessionState): SignedInSessionComponent {
    return sessions.getValue(state.id)
  }

}