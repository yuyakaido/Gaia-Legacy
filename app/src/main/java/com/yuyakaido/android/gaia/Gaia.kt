package com.yuyakaido.android.gaia

import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.AppLifecycleObserver
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import com.yuyakaido.android.gaia.support.SupportNotificationManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class Gaia : DaggerApplication() {

  @Inject
  internal lateinit var appLifecycleObserver: AppLifecycleObserver

  @Inject
  internal lateinit var supportNotificationManager: SupportNotificationManager

  @Inject
  internal lateinit var appStore: AppStore

  @Inject
  internal lateinit var runningSession: RunningSession

  private lateinit var appComponent: AppComponent

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    appComponent = DaggerAppComponent
      .builder()
      .application(this)
      .build()
    return appComponent
  }

  override fun androidInjector(): AndroidInjector<Any> {
    val state = appStore.stateAsValue()
    val component = if (state.sessions.isEmpty()) {
      appComponent.newSessionComponent().build()
    } else {
      runningSession.add(
        state = state.session,
        component = appComponent
      )
      runningSession.component(state.session)
    }
    return component.androidInjector()
  }

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
    initializeStetho()
    initializeAppLifecycleObserver()
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

  private fun initializeSupportNotificationManager() {
    supportNotificationManager.initialize()
  }

}