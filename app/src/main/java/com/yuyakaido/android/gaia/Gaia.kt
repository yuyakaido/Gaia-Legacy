package com.yuyakaido.android.gaia

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.AppCompletionObserver
import com.yuyakaido.android.gaia.core.AppLifecycleObserver
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.ComponentHandler
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import com.yuyakaido.android.gaia.support.SupportNotificationManager
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class Gaia : Application(), HasAndroidInjector {

  @Inject
  internal lateinit var appLifecycleObserver: AppLifecycleObserver

  @Inject
  internal lateinit var appCompletionObserver: AppCompletionObserver

  @Inject
  internal lateinit var supportNotificationManager: SupportNotificationManager

  @Inject
  internal lateinit var appStore: AppStore

  @Inject
  internal lateinit var componentHandler: ComponentHandler

  override fun androidInjector(): AndroidInjector<Any> {
    return componentHandler.activeInjector()
  }

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
    initializeStetho()
    initializeAppLifecycleObserver()
    initializeAppCompletionObserver()
    initializeSupportNotificationManager()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initializeStetho() {
    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
    }
  }

  private fun initializeAppLifecycleObserver() {
    GlobalScope.launch {
      appStore.lifecycleAsFlow()
        .collect { Timber.v("AppLifecycle = $it") }
    }
    ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
  }

  private fun initializeAppCompletionObserver() {
    appCompletionObserver.initialize()
  }

  private fun initializeSupportNotificationManager() {
    supportNotificationManager.initialize()
  }

}