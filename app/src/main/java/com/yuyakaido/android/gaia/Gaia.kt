package com.yuyakaido.android.gaia

import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.AppCompletionObserver
import com.yuyakaido.android.gaia.core.AppLifecycleObserver
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import com.yuyakaido.android.gaia.core.domain.entity.Session
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
  internal lateinit var appCompletionObserver: AppCompletionObserver

  @Inject
  internal lateinit var supportNotificationManager: SupportNotificationManager

  @Inject
  internal lateinit var appStore: AppStore

  @Inject
  internal lateinit var runningSession: RunningSession

  private lateinit var appComponent: AppComponent

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    appComponent = DaggerAppComponent
      .factory()
      .create(this)
    return appComponent
  }

  override fun androidInjector(): AndroidInjector<Any> {
    val state = appStore.stateAsValue()
    return if (state.sessions.isEmpty()) {
      appComponent
        .newSignedOutSessionComponentFactory()
        .create()
        .androidInjector()
    } else {
      when (val session = state.session) {
        is SessionState.SignedOut -> {
          appComponent
            .newSignedOutSessionComponentFactory()
            .create()
            .androidInjector()
        }
        is SessionState.SigningIn -> {
          val signingIn = Session.SigningIn(session.id)
          val module = SignedInSessionModule(signingIn)
          appComponent
            .newSignedInSessionComponentFactory()
            .create(module)
            .androidInjector()
        }
        is SessionState.SignedIn -> {
          runningSession.add(
            state = session,
            component = appComponent
          )
          runningSession
            .component(state.session)
            .androidInjector()
        }
      }
    }
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