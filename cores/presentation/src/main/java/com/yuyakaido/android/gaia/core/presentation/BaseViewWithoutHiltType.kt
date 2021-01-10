package com.yuyakaido.android.gaia.core.presentation

import androidx.lifecycle.Lifecycle

interface BaseViewWithoutHiltType<VM : BaseViewModel> {

  val viewModel: VM

  fun bindLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(viewModel)
  }

  fun unbindLifecycle(lifecycle: Lifecycle) {
    lifecycle.removeObserver(viewModel)
  }

}