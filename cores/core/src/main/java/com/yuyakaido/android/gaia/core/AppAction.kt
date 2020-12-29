package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.ActionType

sealed class AppAction : ActionType<AppState> {
  object ClearSession : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        sessions = emptyList()
      )
    }
  }
  data class AddSignedOutSession(
    private val id: String
  ) : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.add(SessionState.SignedOut(id))
    }
  }
  data class AddSigningInSession(
    private val id: String
  ) : AppAction() {
    override fun reduce(state: AppState): AppState {
      val signingIn = SessionState.SigningIn(
        id = id,
        token = null
      )
      return state.add(signingIn)
    }
  }
  data class ReplaceSession(
    private val target: SessionState
  ) : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.replace(target)
    }
  }
  data class SwitchSession(
    private val id: String
  ) : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.switch(id)
    }
  }
  data class UpdateLifecycle(val lifecycle: AppLifecycle) : AppAction() {
    override fun reduce(state: AppState): AppState {
      return state.copy(
        lifecycle = lifecycle
      )
    }
  }
}
