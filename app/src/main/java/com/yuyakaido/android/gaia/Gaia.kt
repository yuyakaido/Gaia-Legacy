package com.yuyakaido.android.gaia

import android.app.Application
import com.facebook.stetho.Stetho

class Gaia : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}