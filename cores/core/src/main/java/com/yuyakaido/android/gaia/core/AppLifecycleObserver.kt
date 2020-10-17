package com.yuyakaido.android.gaia.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject

// https://qiita.com/takusemba/items/92e700ee6c24a3398d03
// https://developer.android.com/reference/androidx/lifecycle/ProcessLifecycleOwner
class AppLifecycleObserver @Inject constructor(
  private val appStore: AppStore
) : LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onAppCreate() {
    appStore.dispatch(AppAction.ClearSession)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onAppAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    appStore.dispatch(AppAction.UpdateLifecycle(AppLifecycle.from(event)))
  }

}