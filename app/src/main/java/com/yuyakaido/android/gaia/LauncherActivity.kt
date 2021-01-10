package com.yuyakaido.android.gaia

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.presentation.BaseActivityWithHilt
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : BaseActivityWithHilt() {

  @Inject
  lateinit var appNavigator: AppNavigatorType

  private val viewModel: LauncherViewModel by viewModels()

  override fun getViewModel(): BaseViewModel {
    return viewModel
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToAuth
      .observe(this) {
        startActivity(appNavigator.newAuthActivity(it))
      }
    viewModel.navigateToGateway
      .observe(this) {
        startActivity(appNavigator.newGatewayActivity())
      }
  }

}