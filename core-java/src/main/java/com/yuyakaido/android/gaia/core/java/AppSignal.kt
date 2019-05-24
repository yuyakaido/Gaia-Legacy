package com.yuyakaido.android.gaia.core.java

sealed class AppSignal : SignalType {
    data class OpenSession(val session: Session) : AppSignal()
    data class CloseSession(val session: Session) : AppSignal()
    data class SelectSession(val session: Session) : AppSignal()
}