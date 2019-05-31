package com.yuyakaido.android.gaia.gateway.ui

import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class GatewayActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var gatewayViewModel: GatewayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gateway)
        setupTransition()
        gatewayViewModel.onCreate()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun setupTransition() {
        gatewayViewModel.startAuthorizationActivity
            .subscribeBy { Log.d("Gaia","Let's go to authorization!") }
            .addTo(disposables)
        gatewayViewModel.startHomeActivity
            .subscribeBy { Log.d("Gaia","Let's go to home!") }
            .addTo(disposables)
    }

}