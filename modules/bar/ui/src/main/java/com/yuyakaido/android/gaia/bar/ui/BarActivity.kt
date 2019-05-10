package com.yuyakaido.android.gaia.bar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.yuyakaido.android.gaia.android.FooIntentResolverType
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BarActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var resolver: FooIntentResolverType

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, BarActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)

        val startButton = findViewById<Button>(R.id.start_foo)
        startButton.setOnClickListener { startFooActivity() }
    }

    private fun startFooActivity() {
        startActivity(resolver.getFooActivityIntent(this))
    }

}