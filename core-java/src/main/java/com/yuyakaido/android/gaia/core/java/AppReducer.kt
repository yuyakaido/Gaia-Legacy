package com.yuyakaido.android.gaia.core.java

object AppReducer : ReducerType<AppState, AppAction> {

    override fun reduce(state: AppState, action: AppAction): AppState {
        return when (action) {
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
        }
    }

}