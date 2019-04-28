package com.yuyakaido.android.gaia

import android.app.Activity
import com.yuyakaido.android.gaia.di.AppComponent
import com.yuyakaido.android.gaia.di.AppModule
import com.yuyakaido.android.gaia.di.DaggerAppComponent
import com.yuyakaido.android.gaia.di.SessionComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class Gaia : DaggerApplication() {

    @Inject
    lateinit var appComponent: AppComponent

    private lateinit var sessionComponent: SessionComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .create(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return sessionComponent.activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        replaceSession()
    }

    fun replaceSession() {
        sessionComponent = appComponent.sessionComponentBuilder().build()
    }

}