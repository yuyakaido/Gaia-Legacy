package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle

abstract class BaseFragmentWithHilt : BaseFragment(), BaseViewWithHiltType {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindLifecycle(lifecycle)
  }

  override fun onDestroy() {
    unbindLifecycle(lifecycle)
    super.onDestroy()
  }

}