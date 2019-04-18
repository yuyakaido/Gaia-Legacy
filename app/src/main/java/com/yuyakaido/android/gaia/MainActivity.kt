package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()
    private val repository = RepoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.fetchRepos(query = "Gaia")
            .subscribeBy { Log.d("Gaia", it.toString()) }
            .addTo(disposables)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

}
