package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import com.yuyakaido.android.gaia.domain.GetRepoUseCase
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var usecase: GetRepoUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usecase.getRepos(query = "Gaia")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - MainActivity",
                    "hash = ${usecase.hashCode()}, size = ${repos.size}"
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
