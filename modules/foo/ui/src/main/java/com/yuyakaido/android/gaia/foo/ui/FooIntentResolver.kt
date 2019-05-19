package com.yuyakaido.android.gaia.foo.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.FooIntentResolverType

class FooIntentResolver : FooIntentResolverType {

    override fun getFooActivityIntent(context: Context): Intent {
        return FooActivity.createIntent(context)
    }

}