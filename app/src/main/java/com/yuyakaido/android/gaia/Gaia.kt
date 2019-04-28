package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.di.AppModule
import com.yuyakaido.android.gaia.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Gaia : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .create(this)
    }

}