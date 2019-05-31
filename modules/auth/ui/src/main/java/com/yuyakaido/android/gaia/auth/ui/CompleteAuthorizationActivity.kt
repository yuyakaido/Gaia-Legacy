package com.yuyakaido.android.gaia.auth.ui

import android.os.Bundle
import com.yuyakaido.android.gaia.auth.domain.GetAccessTokenUseCase
import com.yuyakaido.android.gaia.core.android.HomeIntentResolverType
import com.yuyakaido.android.gaia.core.java.AppAction
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CompleteAuthorizationActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var homeIntentResolver: HomeIntentResolverType

    @Inject
    lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_authorization)
        handleUrlScheme()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun handleUrlScheme() {
        intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
                getAccessToken(code)
            }
        }
    }

    private fun getAccessToken(code: String) {
        getAccessTokenUseCase.getAccessToken(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { token -> AppDispatcher.dispatch(AppAction.UpdateToken(token)) }
            .subscribeBy { startHomeActivity() }
            .addTo(disposables)
    }

    private fun startHomeActivity() {
        startActivity(homeIntentResolver.getHomeActivityIntent(this))
        finish()
    }

}