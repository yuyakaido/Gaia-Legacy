package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import timber.log.Timber

abstract class BaseFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    printLog(Lifecycle.Event.ON_CREATE)
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
    super.onDestroy()
  }

  private fun printLog(event: Lifecycle.Event) {
    Timber.v("${this::class.java.simpleName}.${event.name} is called.")
  }

}