package com.yuyakaido.android.gaia.android

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.Repo

interface BarIntentResolverType {
    fun getBarActivityIntent(context: Context, repo: Repo): Intent
}