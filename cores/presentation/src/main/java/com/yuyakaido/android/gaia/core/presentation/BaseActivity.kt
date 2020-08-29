package com.yuyakaido.android.gaia.core.presentation

import android.os.Bundle
import com.yuyakaido.android.gaia.core.domain.app.AppNavigatorType
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity(), BaseViewType<VM> {

  @Inject
  override lateinit var appNavigator: AppNavigatorType

  @Inject
  override lateinit var factory: ViewModelFactory<VM>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    bindLifecycle(lifecycle)
  }

  override fun onDestroy() {
    unbindLifecycle(lifecycle)
    super.onDestroy()
  }

}