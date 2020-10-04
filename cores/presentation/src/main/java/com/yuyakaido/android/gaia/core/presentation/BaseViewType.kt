package com.yuyakaido.android.gaia.core.presentation

import androidx.lifecycle.Lifecycle

interface BaseViewType<VM : BaseViewModel> {

  var appNavigator: AppNavigatorType
  var factory: ViewModelFactory<VM>

  val viewModel: VM

  fun bindLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(viewModel)
  }

  fun unbindLifecycle(lifecycle: Lifecycle) {
    lifecycle.removeObserver(viewModel)
  }

}