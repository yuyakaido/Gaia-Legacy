package com.yuyakaido.android.gaia.core.java

sealed class AppSignal : SignalType {
    data class AddSession(val session: SessionState) : AppSignal()
    data class RemoveSession(val session: SessionState) : AppSignal()
    data class SelectSession(val session: SessionState) : AppSignal()
    data class LogOutSession(val session: SessionState) : AppSignal()
    data class LogInSessionWithoutEnv(val session: SessionState, val token: String) : AppSignal()
    data class LogInSessionWithEnv(val session: SessionState, val env: Environment, val token: String) : AppSignal()

    data class ResolveEnvironment(val session: SessionState, val env: Environment) : AppSignal()
    object NavigateToAuth : AppSignal()
    object NavigateToHome : AppSignal()
}