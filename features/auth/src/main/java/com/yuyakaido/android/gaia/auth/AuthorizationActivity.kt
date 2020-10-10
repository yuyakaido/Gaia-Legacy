package com.yuyakaido.android.gaia.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.auth.databinding.ActivityAuthorizationBinding
import com.yuyakaido.android.gaia.core.presentation.BaseActivity

class AuthorizationActivity : BaseActivity<AuthorizationViewModel>() {

  override val viewModel: AuthorizationViewModel by viewModels { factory }
  private val binding by lazy { ActivityAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupNavigation()
  }

  private fun setupNavigation() {
    viewModel.navigateToHome
      .observe(this) {
        startActivity(appNavigator.newAppActivity())
      }
    viewModel.navigateToAuth
      .observe(this) {
        startActivity(appNavigator.newAuthActivity())
      }
  }

}