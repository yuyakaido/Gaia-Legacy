package com.yuyakaido.android.gaia.repo.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.yuyakaido.android.gaia.core.java.Repo
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RepoDetailActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var viewModel: RepoDetailViewModel

    companion object {
        private const val REPO = "REPO"

        fun createIntent(context: Context, repo: Repo): Intent {
            return Intent(context, RepoDetailActivity::class.java)
                .apply { putExtra(REPO, repo) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setupToolbar()
        setupRepo()
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

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRepo() {
        viewModel.repo()
            .subscribeBy { repo ->
                val idTextView = findViewById<TextView>(R.id.id)
                val nameTextView = findViewById<TextView>(R.id.name)
                nameTextView.text = repo.name
                idTextView.text = repo.id.toString()
            }
            .addTo(disposables)
    }

    fun getRepo(): Repo {
        return intent.getSerializableExtra(REPO) as Repo
    }

}