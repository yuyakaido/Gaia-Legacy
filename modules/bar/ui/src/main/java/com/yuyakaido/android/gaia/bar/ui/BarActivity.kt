package com.yuyakaido.android.gaia.bar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.yuyakaido.android.gaia.core.android.FooIntentResolverType
import com.yuyakaido.android.gaia.core.java.Repo
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class BarActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var resolver: FooIntentResolverType

    @Inject
    lateinit var viewModel: BarViewModel

    private val disposables = CompositeDisposable()

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

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.repo()
            .subscribeBy { repo ->
                val startButton = findViewById<Button>(R.id.start_foo)
                startButton.setOnClickListener { startFooActivity() }

                val idTextView = findViewById<TextView>(R.id.id)
                idTextView.text = repo.id.toString()

                val nameTextView = findViewById<TextView>(R.id.name)
                nameTextView.text = repo.name
            }
            .addTo(disposables)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFooActivity() {
        startActivity(resolver.getFooActivityIntent(this))
    }

    fun getRepo(): Repo {
        return intent.getSerializableExtra(REPO) as Repo
    }

}