package com.yuyakaido.android.gaia.home

import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.BaseActivity
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.ViewModelFactory
import com.yuyakaido.android.gaia.home.databinding.ActivityHomeBinding
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivity() {

  @Inject
  internal lateinit var factory: ViewModelFactory<HomeViewModel>

  private val viewModel: HomeViewModel by viewModels { factory }

  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.d("activity = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
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
