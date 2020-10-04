package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment(), BaseViewType<VM> {

  @Inject
  override lateinit var appNavigator: AppNavigatorType

  @Inject
  override lateinit var factory: ViewModelFactory<VM>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    printLog(Lifecycle.Event.ON_CREATE)
    bindLifecycle(lifecycle)
  }

  override fun onStart() {
    super.onStart()
    printLog(Lifecycle.Event.ON_START)
  }

  override fun onResume() {
    super.onResume()
    printLog(Lifecycle.Event.ON_RESUME)
  }

  override fun onPause() {
    super.onPause()
    printLog(Lifecycle.Event.ON_PAUSE)
  }

  override fun onStop() {
    super.onStop()
    printLog(Lifecycle.Event.ON_STOP)
  }

  override fun onDestroy() {
    printLog(Lifecycle.Event.ON_DESTROY)
    unbindLifecycle(lifecycle)
    super.onDestroy()
  }

  private fun printLog(event: Lifecycle.Event) {
    Timber.v("${this::class.java.simpleName}.${event.name} is called.")
  }

}