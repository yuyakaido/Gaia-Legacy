package com.yuyakaido.android.gaia.core.java

sealed class AppAction : ActionType {
    data class UpdateLifecycle(val lifecycle: AppLifecycle) : AppAction()
    data class AddSession(val session: SessionState) : AppAction()
    data class RemoveSession(val session: SessionState) : AppAction()
    data class SelectSession(val session: SessionState) : AppAction()
    data class RestoreSessions(val sessions: List<SessionState>) : AppAction()
    data class LogOutSession(val session: SessionState) : AppAction()
    data class LogInSession(val session: SessionState) : AppAction()
}