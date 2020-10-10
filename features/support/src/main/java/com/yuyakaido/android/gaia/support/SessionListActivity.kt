package com.yuyakaido.android.gaia.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import com.yuyakaido.android.gaia.support.databinding.ActivitySessionListBinding

class SessionListActivity : BaseActivity<SessionListViewModel>() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, SessionListActivity::class.java)
    }
  }

  override val viewModel: SessionListViewModel by viewModels { factory }
  private val binding by lazy { ActivitySessionListBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

}