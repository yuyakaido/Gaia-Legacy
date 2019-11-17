package com.yuyakaido.android.gaia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yuyakaido.android.gaia.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

  private val viewModel by viewModels<HomeViewModel>()
  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupViewPager()
  }

  private fun setupViewPager() {
    binding.viewPager.adapter = SubredditListFragmentPagerAdapter(supportFragmentManager)
  }

}
