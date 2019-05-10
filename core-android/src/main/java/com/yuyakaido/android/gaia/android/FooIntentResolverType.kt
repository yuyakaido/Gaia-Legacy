package com.yuyakaido.android.gaia.android

import android.content.Context
import android.content.Intent

interface FooIntentResolverType {
    fun getFooActivityIntent(context: Context): Intent
}