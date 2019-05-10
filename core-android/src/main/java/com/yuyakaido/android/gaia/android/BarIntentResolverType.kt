package com.yuyakaido.android.gaia.android

import android.content.Context
import android.content.Intent

interface BarIntentResolverType {
    fun getBarActivityIntent(context: Context): Intent
}