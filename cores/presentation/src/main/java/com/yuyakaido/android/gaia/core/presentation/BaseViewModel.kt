package com.yuyakaido.android.gaia.core.presentation

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

abstract class BaseViewModel(
  application: Application
) : AndroidViewModel(application), LifecycleObserver {

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  open fun onCreate() = printLog(Lifecycle.Event.ON_CREATE)

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  open fun onStart() = printLog(Lifecycle.Event.ON_START)

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  open fun onResume() = printLog(Lifecycle.Event.ON_RESUME)

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  open fun onPause() = printLog(Lifecycle.Event.ON_PAUSE)

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  open fun onStop() = printLog(Lifecycle.Event.ON_STOP)

  @CallSuper
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  open fun onDestroy() = printLog(Lifecycle.Event.ON_DESTROY)

  private fun printLog(event: Lifecycle.Event) {
    Timber.v("${this::class.java.simpleName}.${event.name} is called.")
  }

}