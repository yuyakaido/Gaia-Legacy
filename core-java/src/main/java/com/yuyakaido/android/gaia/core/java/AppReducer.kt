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
                state.copy(
                    index = sessions.indexOf(action.session),
                    sessions = sessions
                )
            }
            is AppAction.RemoveSession -> {
                val sessions = state.sessions.minus(action.session)
                state.copy(
                    index = sessions.lastIndex,
                    sessions = sessions
                )
            }
            is AppAction.SelectSession -> {
                state.copy(
                    index = state.sessions.indexOf(action.session)
                )
            }
            is AppAction.RestoreSessions -> {
                state.copy(
                    sessions = action.sessions
                )
            }
            is AppAction.UpdateToken -> {
                state.copy(
                    sessions = state.sessions
                        .mapIndexed { index, session ->
                            if (index == state.index) {
                                session.copy(token = action.token)
                            } else {
                                session
                            }
                        }
                )
            }
        }
    }

}