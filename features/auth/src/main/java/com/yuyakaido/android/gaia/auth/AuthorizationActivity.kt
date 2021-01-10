package com.yuyakaido.android.gaia.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.presentation.BaseActivityWithoutHilt
import javax.inject.Inject

class AuthorizationActivity : BaseActivityWithoutHilt<AuthorizationViewModel>() {

  @Inject
  internal lateinit var appNavigator: AppNavigatorType

  override val viewModel: AuthorizationViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToGateway
      .observe(this) {
        startActivity(appNavigator.newGatewayActivity())
      }
  }

}