package com.yuyakaido.android.gaia.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import dagger.android.support.DaggerAppCompatActivity

class LaunchAuthorizationActivity : DaggerAppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LaunchAuthorizationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_authorization)

        val authorizationButton = findViewById<Button>(R.id.authorization_button)
        authorizationButton.setOnClickListener { authorize() }
    }

    private fun authorize() {
        val uri = Uri.parse("https://github.com/login/oauth/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .appendQueryParameter("scope", "user repo")
            .build()
        startActivity(Intent(Intent.ACTION_VIEW, uri))
        finish()
    }

}