package com.yuyakaido.android.gaia.gateway.ui

import android.os.Bundle
import com.yuyakaido.android.gaia.core.android.AuthorizationIntentResolverType
import com.yuyakaido.android.gaia.core.android.HomeIntentResolverType
import com.yuyakaido.android.gaia.gateway.ui.databinding.ActivityGatewayBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GatewayActivity : DaggerAppCompatActivity() {

  private val disposables = CompositeDisposable()
  private val binding by lazy { ActivityGatewayBinding.inflate(layoutInflater) }

  @Inject
  lateinit var gatewayViewModel: GatewayViewModel

  @Inject
  lateinit var authIntentResolver: AuthorizationIntentResolverType

  @Inject
  lateinit var homeIntentResolver: HomeIntentResolverType

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupTransition()
    gatewayViewModel.onCreate()
  }

  override fun onDestroy() {
    disposables.dispose()
    super.onDestroy()
  }

  private fun setupTransition() {
    gatewayViewModel.startAuthorizationActivity
      .doOnNext { finish() }
      .subscribeBy { startActivity(authIntentResolver.getAuthorizationActivityIntent(this)) }
      .addTo(disposables)
    gatewayViewModel.startHomeActivity
      .doOnNext { finish() }
      .subscribeBy { startActivity(homeIntentResolver.getHomeActivityIntent(this)) }
      .addTo(disposables)
  }

}