package com.yuyakaido.android.gaia.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.yuyakaido.android.gaia.auth.databinding.ActivityAuthorizationBinding
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.infrastructure.Constant
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthorizationActivity : DaggerAppCompatActivity() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<AuthorizationViewModel>

  private val viewModel: AuthorizationViewModel by viewModels { factory }
  private val binding by lazy { ActivityAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupNavigation()
    viewModel.onBind(intent)
  }

  private fun setupNavigation() {
    viewModel.navigateToHome
      .observe(this) {
        startActivity(appRouter.newHomeActivity())
      }
    viewModel.navigateToAuth
      .observe(this) {
        val uri = Uri.Builder()
          .scheme(Constant.OAUTH_SCHEME)
          .encodedAuthority(Constant.OAUTH_AUTHORITY)
          .encodedPath(Constant.OAUTH_PATH)
          .appendQueryParameter("client_id", Constant.OAUTH_CLIENT_ID)
          .appendQueryParameter("response_type", Constant.OAUTH_RESPONSE_TYPE)
          .appendQueryParameter("state", System.currentTimeMillis().toString())
          .appendQueryParameter("redirect_uri", Constant.OAUTH_REDIRECT_URI)
          .appendQueryParameter("duration", Constant.OAUTH_DURATION)
          .appendQueryParameter("scope", Constant.OAUTH_SCOPES.joinToString(" "))
          .build()
        startActivity(Intent(Intent.ACTION_VIEW, uri))
      }
  }

}