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
                        .mapIndexed { index, session ->
                            if (index == nextIndex) {
                                session.copy(status = Session.Status.Active)
                            } else {
                                session.copy(status = Session.Status.Inactive)
                            }
                        }
                )
            }
            is AppAction.RemoveSession -> {
                val sessions = state.sessions.minus(action.session)
                val nextIndex = sessions.lastIndex
                state.copy(
                    index = nextIndex,
                    sessions = sessions
                        .mapIndexed { index, session ->
                            if (index == nextIndex) {
                                session.copy(status = Session.Status.Active)
                            } else {
                                session.copy(status = Session.Status.Inactive)
                            }
                        }
                )
            }
            is AppAction.SelectSession -> {
                val nextIndex = state.sessions.indexOf(action.session)
                state.copy(
                    index = nextIndex,
                    sessions = state.sessions
                        .mapIndexed { index, session ->
                            if (index == nextIndex) {
                                session.copy(status = Session.Status.Active)
                            } else {
                                session.copy(status = Session.Status.Inactive)
                            }
                        }
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