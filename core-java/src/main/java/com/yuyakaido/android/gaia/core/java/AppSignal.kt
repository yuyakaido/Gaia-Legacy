package com.yuyakaido.android.gaia.core.java

sealed class AppSignal : SignalType {
    data class OpenSession(val environment: Environment) : AppSignal()
    data class CloseSession(val environment: Environment) : AppSignal()
}