package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.StateType

data class AppState(
  val lifecycle: AppLifecycle = AppLifecycle.OnAny,
  val index: Int = 0,
  val sessions: List<SessionState> = emptyList()
) : StateType {

  val session get() = sessions[index]
  val signedIn get() = session as SessionState.SignedIn

  val article get() = signedIn.article
  val community get() = signedIn.community

  fun update(next: SessionState.SignedIn): AppState {
    return copy(
      sessions = sessions.map { current ->
        if (current.id == next.id) {
          next
        } else {
          current
        }
      }
    )
  }

  fun update(article: ArticleState): AppState {
    return update(
      next = signedIn.copy(
        article = article
      )
    )
  }

  fun update(community: CommunityState): AppState {
    return update(
      next = signedIn.copy(
        community = community
      )
    )
  }

  fun add(target: SessionState): AppState {
    val none = sessions.none { it.id == target.id }
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

  fun replace(target: SessionState): AppState {
    return copy(
      sessions = sessions.map { current ->
        if (current.id == target.id) {
          target
        } else {
          current
        }
      }
    )
  }

  fun switch(id: String): AppState {
    return copy(
      index = sessions.indexOfFirst { it.id == id }
    )
  }

}