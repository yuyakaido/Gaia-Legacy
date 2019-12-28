package com.yuyakaido.android.gaia.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.yuyakaido.android.gaia.auth.databinding.ActivityAuthorizationBinding
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AuthTokenServiceType
import com.yuyakaido.android.gaia.core.infrastructure.Constant
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationActivity : DaggerAppCompatActivity() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var authTokenService: AuthTokenServiceType

  @Inject
  internal lateinit var api: PublicApi

  private val binding by lazy { ActivityAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    dispatch()
  }

  private fun dispatch() {
    val token = authTokenService.current()
    when {
      intent.data != null -> {
        GlobalScope.launch {
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              val response = api.getInitialToken(code = code)
              authTokenService.save(response.toAuthToken())
              startActivity(appRouter.newHomeActivity())
            }
          }
        }
      }
      token.isLoggedIn() -> {
        startActivity(appRouter.newHomeActivity())
      }
      else -> {
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

}