package com.yuyakaido.android.gaia.auth

import android.os.Bundle
import com.yuyakaido.android.gaia.auth.databinding.ActivityLaunchAuthorizationBinding
import com.yuyakaido.android.gaia.core.presentation.BaseActivity

class LaunchAuthorizationActivity : BaseActivity() {

  private val binding by lazy { ActivityLaunchAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

}