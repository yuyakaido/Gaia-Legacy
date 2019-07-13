package com.yuyakaido.android.gaia.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.EnvironmentIntentResolverType

class EnvironmentIntentResolver : EnvironmentIntentResolverType {

    override fun getEnvironmentActivityIntent(context: Context): Intent {
        return Intent(context, SelectEnvironmentActivity::class.java)
    }

}