package com.yuyakaido.android.gaia.core

sealed class AppSignal : SignalType {
    object OpenSession : AppSignal()
    object CloseSession : AppSignal()
}