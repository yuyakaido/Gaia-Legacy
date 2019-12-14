package com.yuyakaido.android.gaia.auth

import android.os.Bundle
import com.yuyakaido.android.gaia.auth.databinding.ActivityCompleteAuthorizationBinding
import com.yuyakaido.android.gaia.core.app.GaiaType
import com.yuyakaido.android.gaia.core.infrastructure.RedditWwwService
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import com.yuyakaido.android.gaia.core.value.AccessToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompleteAuthorizationActivity : BaseActivity() {

  @Inject
  internal lateinit var service: RedditWwwService

  private val binding by lazy { ActivityCompleteAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    GlobalScope.launch {
      intent.data?.let { uri ->
        uri.getQueryParameter("code")?.let { code ->
          val response = service.accessToken(code = code)
          val token = AccessToken(value = response.accessToken)
          token.save(this@CompleteAuthorizationActivity)
          val app = application as GaiaType
          startActivity(app.newHomeActivity())
        }
      }
    }
  }

}