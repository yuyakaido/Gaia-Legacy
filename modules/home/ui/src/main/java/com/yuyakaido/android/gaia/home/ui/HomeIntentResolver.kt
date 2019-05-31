package com.yuyakaido.android.gaia.home.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.HomeIntentResolverType

class HomeIntentResolver : HomeIntentResolverType {

    override fun getHomeActivityIntent(context: Context): Intent {
        return Intent(context, HomeActivity::class.java)
    }

}