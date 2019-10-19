package com.yuyakaido.android.gaia

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yuyakaido.android.gaia.core.java.AppAction
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppLifecycle

// https://qiita.com/takusemba/items/92e700ee6c24a3398d03
// https://developer.android.com/reference/android/arch/lifecycle/ProcessLifecycleOwner
class AppLifecycleObserver : LifecycleObserver {

  private fun toAppLifecycle(event: Lifecycle.Event): AppLifecycle {
    return when (event) {
      Lifecycle.Event.ON_CREATE -> AppLifecycle.OnCreate
      Lifecycle.Event.ON_START -> AppLifecycle.OnStart
      Lifecycle.Event.ON_RESUME -> AppLifecycle.OnResume
      Lifecycle.Event.ON_PAUSE -> AppLifecycle.OnPause
      Lifecycle.Event.ON_STOP -> AppLifecycle.OnStop
      Lifecycle.Event.ON_DESTROY -> AppLifecycle.OnDestroy
      Lifecycle.Event.ON_ANY -> AppLifecycle.OnAny
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onAppCreate() {
    // プロセス起動時に1度だけ呼び出される
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_CREATE)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onAppStart() {
    // プロセス内で最初のActivity#onStartを通過すると呼び出される
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_START)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onAppResume() {
    // プロセス内で最初のActivity#onResumeを通過すると呼び出される
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_RESUME)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onAppPause() {
    // プロセス内で最後のActivity#onPauseを通過すると呼び出される
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_PAUSE)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onAppStop() {
    // プロセス内で最後のActivity#onStopを通過すると呼び出される
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_STOP)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onAppDestroy() {
    // このメソッドが呼び出されることはない
    // https://developer.android.com/reference/android/arch/lifecycle/ProcessLifecycleOwner
    AppDispatcher.dispatch(
      AppAction.UpdateLifecycle(
        lifecycle = toAppLifecycle(Lifecycle.Event.ON_DESTROY)
      )
    )
  }

}
