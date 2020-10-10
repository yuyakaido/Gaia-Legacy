package com.yuyakaido.android.gaia

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.BaseActivity

class GatewayActivity : BaseActivity<GatewayViewModel>() {

  override val viewModel: GatewayViewModel by viewModels { factory }

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