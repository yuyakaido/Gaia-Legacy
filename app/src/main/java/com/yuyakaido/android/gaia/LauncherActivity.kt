package com.yuyakaido.android.gaia

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.BaseActivity

class LauncherActivity : BaseActivity<LauncherViewModel>() {

  override val viewModel: LauncherViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToAuth
      .observe(this) {
        startActivity(appNavigator.newAuthActivity(it))
      }
    viewModel.navigateToApp
      .observe(this) {
        startActivity(appNavigator.newAppActivity())
      }
  }

}