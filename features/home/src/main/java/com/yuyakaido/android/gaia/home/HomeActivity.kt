package com.yuyakaido.android.gaia.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yuyakaido.android.gaia.home.databinding.ActivityHomeBinding
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, HomeActivity::class.java)
        .apply {
          addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
  }

  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

}
