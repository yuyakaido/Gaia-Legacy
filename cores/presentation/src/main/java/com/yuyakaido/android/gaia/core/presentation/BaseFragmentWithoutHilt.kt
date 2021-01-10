package com.yuyakaido.android.gaia.core.presentation

import android.content.Context
import android.os.Bundle
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragmentWithoutHilt<VM : BaseViewModel> : BaseFragment(), BaseViewWithoutHiltType<VM> {

  @Inject
  protected lateinit var factory: ViewModelFactory<VM>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindLifecycle(lifecycle)
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onDestroy() {
    unbindLifecycle(lifecycle)
    super.onDestroy()
  }

}