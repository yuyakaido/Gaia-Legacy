package com.yuyakaido.android.gaia.home.ui

import android.os.Bundle
import com.yuyakaido.android.gaia.core.android.ProfileFragmentResolverType
import com.yuyakaido.android.gaia.core.android.RepoFragmentResolverType
import com.yuyakaido.android.gaia.home.ui.databinding.ActivityHomeBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    @Inject
    lateinit var repoFragmentResolver: RepoFragmentResolverType

    @Inject
    lateinit var profileFragmentResolver: ProfileFragmentResolverType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = HomeFragmentPager(
            manager = supportFragmentManager,
            repoFragmentResolver = repoFragmentResolver,
            profileFragmentResolver = profileFragmentResolver
        )
    }

}