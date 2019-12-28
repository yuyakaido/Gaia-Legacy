package com.yuyakaido.android.gaia.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.home.databinding.ActivityHomeBinding
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

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

  @Inject
  internal lateinit var appRouter: AppRouterType

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
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, page.fragment.invoke(appRouter))
      .commitNow()
  }

}
