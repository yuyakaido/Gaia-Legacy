package com.yuyakaido.android.gaia.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import javax.inject.Inject

class AppCompletionObserver @Inject constructor(
  private val application: Application,
  private val appStore: AppStore
) : LifecycleObserver {

  private var activityCount = 0

  fun initialize() {
    application.registerActivityLifecycleCallbacks(
      object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
          activityCount++
        }
        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityResumed(activity: Activity) = Unit
        override fun onActivityPaused(activity: Activity) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
        override fun onActivityDestroyed(activity: Activity) {
          activityCount--
          if (activityCount == 0) {
            appStore.dispatch(AppAction.ClearSession)
          }
        }
      }
    )
  }

}