package com.yuyakaido.android.gaia.core.presentation

import androidx.lifecycle.Lifecycle

interface BaseViewWithHiltType {

  fun getViewModel(): BaseViewModel

  fun bindLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(getViewModel())
  }

  fun unbindLifecycle(lifecycle: Lifecycle) {
    lifecycle.removeObserver(getViewModel())
  }

}