package com.yuyakaido.android.gaia.core.java

sealed class AppSignal : SignalType {
    data class AddSession(val session: SessionState) : AppSignal()
    data class RemoveSession(val session: SessionState) : AppSignal()
    data class SelectSession(val session: SessionState) : AppSignal()
    data class LogOutSession(val session: SessionState) : AppSignal()
    data class LogInSession(val session: SessionState, val token: String) : AppSignal()
}