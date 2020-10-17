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
        .apply {
          addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
  }

  override val viewModel: GatewayViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToApp
      .observe(this) {
        startActivity(appNavigator.newAppActivity())
      }
  }

}