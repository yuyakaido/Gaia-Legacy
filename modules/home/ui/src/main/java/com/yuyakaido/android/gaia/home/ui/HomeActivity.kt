package com.yuyakaido.android.gaia.home.ui

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.yuyakaido.android.gaia.core.android.ProfileFragmentResolverType
import com.yuyakaido.android.gaia.core.android.RepoFragmentResolverType
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repoFragmentResolver: RepoFragmentResolverType

    @Inject
    lateinit var profileFragmentResolver: ProfileFragmentResolverType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = HomeFragmentPager(
            manager = supportFragmentManager,
            repoFragmentResolver = repoFragmentResolver,
            profileFragmentResolver = profileFragmentResolver
        )
    }

}