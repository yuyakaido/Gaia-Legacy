package com.yuyakaido.android.gaia.user.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import com.yuyakaido.android.gaia.user.detail.databinding.ActivityUserDetailBinding

class UserDetailActivity : BaseActivity() {

  companion object {
    private val USER = User::class.java.simpleName

    fun createIntent(context: Context, user: User): Intent {
      return Intent(context, UserDetailActivity::class.java)
        .apply { putExtra(USER, user) }
    }
  }

  private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

  private fun getUser(): User {
    return requireNotNull(intent.getParcelableExtra(USER))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupDetail()
  }

  private fun setupDetail() {
    binding.name.text = getUser().name
  }

}