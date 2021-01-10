package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivityWithoutHilt<VM : BaseViewModel> : BaseActivity(), BaseViewWithoutHiltType<VM> {

  @Inject
  protected lateinit var factory: ViewModelFactory<VM>

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    bindLifecycle(lifecycle)
  }

  override fun onDestroy() {
    unbindLifecycle(lifecycle)
    super.onDestroy()
  }

}