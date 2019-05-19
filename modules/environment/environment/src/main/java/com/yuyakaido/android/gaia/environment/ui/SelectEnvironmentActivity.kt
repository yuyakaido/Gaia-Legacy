package com.yuyakaido.android.gaia.environment.ui

import android.os.Bundle
import com.yuyakaido.android.gaia.core.android.AuthorizationIntentResolverType
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SelectEnvironmentActivity : DaggerAppCompatActivity(), SelectEnvironmentDialog.OnDismissListener {

    @Inject
    lateinit var resolver: AuthorizationIntentResolverType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_environment)

        val dialog =
            SelectEnvironmentDialog.newInstance()
        dialog.show(supportFragmentManager, SelectEnvironmentDialog::class.java.simpleName)
    }

    override fun onDismiss() {
        startActivity(resolver.getAuthorizationIntent(this))
        finish()
    }

}