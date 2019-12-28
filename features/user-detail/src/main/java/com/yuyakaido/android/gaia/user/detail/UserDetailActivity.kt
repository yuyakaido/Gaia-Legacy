package com.yuyakaido.android.gaia.user.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.user.detail.databinding.ActivityUserDetailBinding
import dagger.android.support.DaggerAppCompatActivity

class UserDetailActivity : DaggerAppCompatActivity() {

  companion object {
    private val USER = User::class.java.simpleName

    fun createIntent(context: Context, user: User): Intent {
      return Intent(context, UserDetailActivity::class.java)
        .apply { putExtra(USER, user) }
    }
  }

  private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupFragment()
  }

  private fun setupFragment() {
    val user = requireNotNull(intent.getParcelableExtra<User>(USER))
    val fragment = UserDetailFragment.newInstanceForUser(user = user)
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commitNow()
  }

}