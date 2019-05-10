package com.yuyakaido.android.gaia.bar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.yuyakaido.android.gaia.android.FooIntentResolverType
import com.yuyakaido.android.gaia.core.Repo
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BarActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var resolver: FooIntentResolverType

    companion object {
        private const val REPO = "REPO"

        fun createIntent(context: Context, repo: Repo): Intent {
            return Intent(context, BarActivity::class.java)
                .apply { putExtra(REPO, repo) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)

        val repo = intent.getSerializableExtra(REPO) as Repo

        val startButton = findViewById<Button>(R.id.start_foo)
        startButton.setOnClickListener { startFooActivity() }

        val idTextView = findViewById<TextView>(R.id.id)
        idTextView.text = repo.id.toString()

        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = repo.name
    }

    private fun startFooActivity() {
        startActivity(resolver.getFooActivityIntent(this))
    }

}