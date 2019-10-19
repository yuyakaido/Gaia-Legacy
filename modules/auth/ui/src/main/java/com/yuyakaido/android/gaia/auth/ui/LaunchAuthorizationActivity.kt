package com.yuyakaido.android.gaia.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.yuyakaido.android.gaia.auth.ui.databinding.ActivityLaunchAuthorizationBinding
import com.yuyakaido.android.gaia.core.java.SessionState
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LaunchAuthorizationActivity : DaggerAppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, LaunchAuthorizationActivity::class.java)
    }
  }

  @Inject
  lateinit var session: SessionState

  private val binding by lazy { ActivityLaunchAuthorizationBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    binding.environmentText.text = session.toString()
    binding.authorizationButton.setOnClickListener { authorize() }
  }

  private fun authorize() {
    val uri = Uri.parse("https://github.com/login/oauth/authorize")
      .buildUpon()
      .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
      .appendQueryParameter("scope", "user repo")
      .build()
    startActivity(Intent(Intent.ACTION_VIEW, uri))
    finish()
  }

}