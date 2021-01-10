package com.yuyakaido.android.gaia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.presentation.BaseActivityWithHilt
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GatewayActivity : BaseActivityWithHilt() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, GatewayActivity::class.java)
        .apply {
          addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
  }

  @Inject
  internal lateinit var appNavigator: AppNavigatorType

  private val viewModel: GatewayViewModel by viewModels()

  override fun getViewModel(): BaseViewModel {
    return viewModel
  }

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