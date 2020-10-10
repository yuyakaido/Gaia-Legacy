package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.StateType

data class AppState(
  val lifecycle: AppLifecycle = AppLifecycle.OnAny,
  val index: Int = 0,
  val sessions: List<SessionState> = emptyList()
) : StateType {

  val session get() = sessions[index]
  val signedIn get() = session as SessionState.SignedIn

  fun update(next: SessionState.SignedIn): AppState {
    return copy(
      sessions = sessions.map { current ->
        if (current.id() == next.id()) {
          next
        } else {
          current
        }
      }
    )
  }

  fun add(target: SessionState): AppState {
    val none = sessions.none { it.id() == target.id() }
    val nextSessions = if (none) {
      sessions.plus(target)
    } else {
      sessions
    }
    val nextIndex = if (none) {
      nextSessions.lastIndex
    } else {
      index
    }
    return copy(
      index = nextIndex,
      sessions = nextSessions
    )
  }

  fun replace(state: String, target: SessionState.SignedIn): AppState {
    val isNewAccount = sessions.none { it.id() == target.id() }
    return if (isNewAccount) {
      copy(
        sessions = sessions.map { current ->
          if (current.id() == state) {
            target
          } else {
            current
          }
        }
      )
    } else {
      val nextSessions = sessions.filterNot { current -> current.id() == state }
      val nextIndex = nextSessions.lastIndex
      copy(
        index = nextIndex,
        sessions = nextSessions
      )
    }
  }

}