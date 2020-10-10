package com.yuyakaido.android.gaia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.BaseActivity

class GatewayActivity : BaseActivity<GatewayViewModel>() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, GatewayActivity::class.java)
    }
  }

  override val viewModel: GatewayViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToAuth
      .observe(this) {
        startActivity(appNavigator.newAuthActivity(it))
        finish()
      }
    viewModel.navigateToApp
      .observe(this) {
        startActivity(appNavigator.newAppActivity())
        finish()
      }
  }

}