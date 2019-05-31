package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface GatewayIntentResolverType {
    fun getGatewayActivityIntent(context: Context): Intent
}