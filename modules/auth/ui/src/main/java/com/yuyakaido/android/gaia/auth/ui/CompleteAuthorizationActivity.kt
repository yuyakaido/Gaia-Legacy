package com.yuyakaido.android.gaia.auth.ui

import android.os.Bundle
import com.yuyakaido.android.gaia.auth.domain.GetAccessTokenUseCase
import com.yuyakaido.android.gaia.auth.ui.databinding.ActivityCompleteAuthorizationBinding
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.SessionState
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CompleteAuthorizationActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()
    private val binding by lazy { ActivityCompleteAuthorizationBinding.inflate(layoutInflater) }

    @Inject
    lateinit var session: SessionState

    @Inject
    lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
            .subscribeBy { token -> AppDispatcher.dispatch(AppSignal.LogInSession(session, token)) }
            .addTo(disposables)
    }

}