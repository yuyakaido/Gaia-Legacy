package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle
import com.yuyakaido.android.gaia.core.domain.app.AppNavigatorType
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment() {

  @Inject
  lateinit var appNavigator: AppNavigatorType

  @Inject
  lateinit var factory: ViewModelFactory<VM>

  abstract val viewModel: VM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycle.addObserver(viewModel)
  }

  override fun onDestroy() {
    lifecycle.removeObserver(viewModel)
    super.onDestroy()
  }

}