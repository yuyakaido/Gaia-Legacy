package com.yuyakaido.android.gaia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
    lateinit var session: Session

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Gaia - MainActivity@${hashCode()}", "session = ${session.hashCode()}")

        viewModel.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - MainActivity@${hashCode()}",
                    "vm = ${viewModel.hashCode()}, repos = ${repos.size}"
                )
            }
            .addTo(disposables)

        val replaceButton = findViewById<Button>(R.id.replace_button)
        replaceButton.setOnClickListener { replaceSession() }
        replaceFragment()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    fun getQuery(): String {
        return "Gaia"
    }

    private fun replaceSession() {
        val gaia = application as Gaia
        gaia.replaceSession()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.primary_fragment_container,
                MainFragment.newInstance()
            )
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.secondary_fragment_container,
                MainFragment.newInstance()
            )
            .commit()
    }

}