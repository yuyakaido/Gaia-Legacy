package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var repository: RepoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.fetchRepos(query = "Gaia")
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - MainActivity",
                    "hash = ${repository.hashCode()}, size = ${repos.size}"
                )
            }
            .addTo(disposables)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

}
