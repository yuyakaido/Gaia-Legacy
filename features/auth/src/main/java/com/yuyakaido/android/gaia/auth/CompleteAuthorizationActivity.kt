package com.yuyakaido.android.gaia.auth

import android.os.Bundle
import com.yuyakaido.android.gaia.auth.databinding.ActivityCompleteAuthorizationBinding
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AuthTokenServiceType
import com.yuyakaido.android.gaia.core.infrastructure.PublicApi
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompleteAuthorizationActivity : DaggerAppCompatActivity() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var authTokenService: AuthTokenServiceType

  @Inject
  internal lateinit var api: PublicApi

  private val binding by lazy { ActivityCompleteAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    authorize()
  }

  private fun authorize() {
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

}