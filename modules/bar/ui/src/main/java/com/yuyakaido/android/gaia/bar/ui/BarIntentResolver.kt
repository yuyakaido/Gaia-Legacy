package com.yuyakaido.android.gaia.bar.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.android.BarIntentResolverType

class BarIntentResolver : BarIntentResolverType {

    override fun getBarActivityIntent(context: Context): Intent {
        return BarActivity.createIntent(context)
    }

}