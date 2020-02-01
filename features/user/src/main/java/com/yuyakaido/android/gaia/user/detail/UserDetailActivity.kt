package com.yuyakaido.android.gaia.user.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.navArgs
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.user.R
import com.yuyakaido.android.gaia.user.databinding.ActivityUserDetailBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserDetailActivity : DaggerAppCompatActivity() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  private val args: UserDetailActivityArgs by navArgs()
  private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupToolbar()
    setupFragment()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> finish()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun setupToolbar() {
    supportActionBar?.title = getString(R.string.user_detail_identity, args.user.name)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun setupFragment() {
    val fragment = appRouter.newUserDetailFragmentForUser(user = args.user)
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commitNow()
  }

}