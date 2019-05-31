package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface HomeIntentResolverType {
    fun getHomeActivityIntent(context: Context): Intent
}