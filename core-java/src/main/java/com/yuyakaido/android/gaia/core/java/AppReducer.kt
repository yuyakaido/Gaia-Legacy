package com.yuyakaido.android.gaia.core.java

object AppReducer : ReducerType<AppState, AppAction> {

    override fun reduce(state: AppState, action: AppAction): AppState {
        return when (action) {
            is AppAction.UpdateLifecycle -> {
                state.copy(
                    lifecycle = action.lifecycle
                )
            }
            is AppAction.AddSession -> {
                val sessions = state.sessions.plus(action.session)
                val nextIndex = sessions.indexOf(action.session)
                state.copy(
                    index = nextIndex,
                    sessions = sessions
                )
            }
            is AppAction.RemoveSession -> {
                val sessions = state.sessions.minus(action.session)
                val nextIndex = sessions.lastIndex
                state.copy(
                    index = nextIndex,
                    sessions = sessions
                )
            }
            is AppAction.SelectSession -> {
                val nextIndex = state.sessions.indexOf(action.session)
                state.copy(
                    index = nextIndex,
                    sessions = state.sessions
                )
            }
            is AppAction.RestoreSessions -> {
                state.copy(
                    sessions = action.sessions
                )
            }
            is AppAction.LogOutSession -> {
                state.copy(
                    sessions = state.sessions
                        .map { session ->
                            if (session.id == action.session.id) {
                                action.session
                            } else {
                                session
                            }
                        }
                )
            }
            is AppAction.LogInSession -> {
                state.copy(
                    sessions = state.sessions
                        .map { session ->
                            if (session.id == action.session.id) {
                                action.session
                            } else {
                                session
                            }
                        }
                )
            }
        }
    }

}