package com.yuyakaido.android.gaia

import androidx.lifecycle.ProcessLifecycleOwner
import com.yuyakaido.android.gaia.core.AppLifecycleObserver
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.app.GaiaType
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class Gaia : GaiaType() {

  @Inject
  internal lateinit var appLifecycleObserver: AppLifecycleObserver

  @Inject
  internal lateinit var appStore: AppStore

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }

  override fun onCreate() {
    super.onCreate()
    initializeAppLifecycleObserver()
  }

  private fun initializeAppLifecycleObserver() {
    GlobalScope.launch {
      appStore.lifecycleAsFlow()
        .collect { Timber.v("AppLifecycle = $it") }
    }
    ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
  }

}