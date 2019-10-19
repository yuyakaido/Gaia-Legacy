package com.yuyakaido.android.gaia.repo.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.yuyakaido.android.gaia.core.java.Repo
import com.yuyakaido.android.gaia.repo.detail.ui.databinding.ActivityRepoDetailBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RepoDetailActivity : DaggerAppCompatActivity() {

  private val disposables = CompositeDisposable()
  private val binding by lazy { ActivityRepoDetailBinding.inflate(layoutInflater) }

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
    setContentView(binding.root)
    setupToolbar()
    setupRepo()
  }

  override fun onDestroy() {
    disposables.dispose()
    super.onDestroy()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> finish()
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
        binding.id.text = repo.name
        binding.name.text = repo.id.toString()
      }
      .addTo(disposables)
  }

  fun getRepo(): Repo {
    return intent.getSerializableExtra(REPO) as Repo
  }

}