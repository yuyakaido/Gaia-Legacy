package com.yuyakaido.android.gaia

import android.app.Activity
import com.yuyakaido.android.gaia.core.AppDispatcher
import com.yuyakaido.android.gaia.core.AppSignal
import com.yuyakaido.android.gaia.core.AvailableEnvironment
import com.yuyakaido.android.gaia.di.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class Gaia : DaggerApplication() {

    @Inject
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var available: AvailableEnvironment

    private lateinit var sessionComponent: SessionComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return sessionComponent.activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        AppDispatcher.on(AppSignal.OpenSession::class.java)
            .doOnSubscribe { setupSession() }
            .subscribeBy { setupSession() }
    }

    private fun setupSession() {
        sessionComponent = appComponent
            .sessionComponentBuilder()
            .sessionModule(
                SessionModule(
                    environment = available.primary()
                )
            )
            .build()
    }

}