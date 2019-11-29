package com.yuyakaido.android.gaia.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.home.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

  private val viewModel by viewModels<HomeViewModel>()
  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupViewPager()
    replaceFragment(HomePage.Popular)
  }

  private fun setupViewPager() {
    binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      replaceFragment(HomePage.from(item.itemId))
      return@setOnNavigationItemSelectedListener true
    }
  }

  private fun replaceFragment(page: HomePage) {
    val app = application as GaiaType
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, page.fragment.invoke(app))
      .commitNow()
  }

}
