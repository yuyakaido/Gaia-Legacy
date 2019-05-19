package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface FooIntentResolverType {
    fun getFooActivityIntent(context: Context): Intent
}