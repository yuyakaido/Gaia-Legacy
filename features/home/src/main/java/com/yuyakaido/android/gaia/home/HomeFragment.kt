package com.yuyakaido.android.gaia.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.presentation.OptionMenuType
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.home.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(), OptionMenuType {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<HomeViewModel>

  private val viewModel: HomeViewModel by viewModels { factory }

  private lateinit var binding: FragmentHomeBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentHomeBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPager()
    replaceFragment(HomePage.Popular)
  }

  override fun shouldShowOptionMenu(): Boolean {
    val id = binding.bottomNavigationView.selectedItemId
    return HomePage.from(id = id).shouldShowOptionMenu
  }

  private fun setupViewPager() {
    binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      replaceFragment(HomePage.from(item.itemId))
      return@setOnNavigationItemSelectedListener true
    }
  }

  private fun replaceFragment(page: HomePage) {
    childFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, page.fragment.invoke(appRouter))
      .commitNow()
  }

}