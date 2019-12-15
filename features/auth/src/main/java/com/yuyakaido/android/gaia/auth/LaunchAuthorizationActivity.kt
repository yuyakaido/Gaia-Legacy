package com.yuyakaido.android.gaia.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.yuyakaido.android.gaia.auth.databinding.ActivityLaunchAuthorizationBinding
import com.yuyakaido.android.gaia.core.domain.app.AccessTokenServiceType
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.infrastructure.Constant
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import javax.inject.Inject

class LaunchAuthorizationActivity : BaseActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, LaunchAuthorizationActivity::class.java)
        .apply {
          addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var accessTokenService: AccessTokenServiceType

  private val binding by lazy { ActivityLaunchAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    dispatch()
  }

  private fun dispatch() {
    val token = accessTokenService.current()
    if (token.isLoggedIn()) {
      startActivity(appRouter.newHomeActivity())
    } else {
      val uri = Uri.Builder()
        .scheme(Constant.OAUTH_SCHEME)
        .encodedAuthority(Constant.OAUTH_AUTHORITY)
        .encodedPath(Constant.OAUTH_PATH)
        .appendQueryParameter("client_id", Constant.OAUTH_CLIENT_ID)
        .appendQueryParameter("response_type", Constant.OAUTH_RESPONSE_TYPE)
        .appendQueryParameter("state", System.currentTimeMillis().toString())
        .appendQueryParameter("redirect_uri", Constant.OAUTH_REDIRECT_URI)
        .appendQueryParameter("scope", Constant.OAUTH_SCOPES.joinToString(" "))
        .build()
      startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
  }

}