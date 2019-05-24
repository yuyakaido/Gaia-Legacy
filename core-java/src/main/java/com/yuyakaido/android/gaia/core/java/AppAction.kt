package com.yuyakaido.android.gaia.core.java

sealed class AppAction : ActionType {
    data class UpdateLifecycle(val lifecycle: AppLifecycle) : AppAction()
    data class AddSession(val session: Session) : AppAction()
    data class RemoveSession(val session: Session) : AppAction()
    data class SelectSession(val session: Session) : AppAction()
    data class RestoreSessions(val sessions: List<Session>) : AppAction()
}