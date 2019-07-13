package com.yuyakaido.android.gaia.home.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yuyakaido.android.gaia.core.android.ProfileFragmentResolverType
import com.yuyakaido.android.gaia.core.android.RepoFragmentResolverType
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.AppStore
import com.yuyakaido.android.gaia.core.java.SessionState
import com.yuyakaido.android.gaia.home.ui.databinding.ActivityHomeBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    @Inject
    lateinit var repoFragmentResolver: RepoFragmentResolverType

    @Inject
    lateinit var profileFragmentResolver: ProfileFragmentResolverType

    @Inject
    lateinit var session: SessionState

    @Inject
    lateinit var appStore: AppStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewPager()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logged_out -> {
                AppDispatcher.dispatch(AppSignal.LogOutSession(session))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = HomeFragmentPager(
            manager = supportFragmentManager,
            repoFragmentResolver = repoFragmentResolver,
            profileFragmentResolver = profileFragmentResolver
        )
    }

}