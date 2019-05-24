package com.yuyakaido.android.gaia.foo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yuyakaido.android.gaia.core.java.Session
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FooActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var session: Session

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FooActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foo)

        Log.d("Gaia - FooActivity@${hashCode()}", "session = ${session.hashCode()}")

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

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

}