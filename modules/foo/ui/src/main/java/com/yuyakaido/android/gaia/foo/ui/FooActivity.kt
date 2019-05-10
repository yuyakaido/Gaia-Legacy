package com.yuyakaido.android.gaia.foo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.yuyakaido.android.gaia.android.BarIntentResolverType
import com.yuyakaido.android.gaia.core.AppDispatcher
import com.yuyakaido.android.gaia.core.AppSignal
import com.yuyakaido.android.gaia.core.Session
import com.yuyakaido.android.gaia.ui.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FooActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var viewModel: FooViewModel

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FooActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foo)

        Log.d("Gaia - FooActivity@${hashCode()}", "session = ${session.hashCode()}")

        viewModel.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - FooActivity@${hashCode()}",
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
        return "Android"
    }

    private fun replaceSession() {
        AppDispatcher.dispatch(AppSignal.OpenSession)

        startActivity(Intent(this, FooActivity::class.java))
        finish()
    }

    private fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.primary_fragment_container,
                FooFragment.newInstance()
            )
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.secondary_fragment_container,
                FooFragment.newInstance()
            )
            .commit()
    }

}