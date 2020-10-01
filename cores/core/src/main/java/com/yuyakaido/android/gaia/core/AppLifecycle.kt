package com.yuyakaido.android.gaia.core

import androidx.lifecycle.Lifecycle

enum class AppLifecycle {
  OnCreate,
  OnStart,
  OnResume,
  OnPause,
  OnStop,
  OnDestroy,
  OnAny;

  companion object {
    fun from(event: Lifecycle.Event): AppLifecycle {
      return when (event) {
        Lifecycle.Event.ON_CREATE -> OnCreate
        Lifecycle.Event.ON_START -> OnStart
        Lifecycle.Event.ON_RESUME -> OnResume
        Lifecycle.Event.ON_PAUSE -> OnPause
        Lifecycle.Event.ON_STOP -> OnStop
        Lifecycle.Event.ON_DESTROY -> OnDestroy
        Lifecycle.Event.ON_ANY -> OnAny
      }
    }
  }
}