package com.yuyakaido.android.gaia.foo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import com.yuyakaido.android.gaia.core.java.Session
import com.yuyakaido.android.gaia.ui.R
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FooActivity : DaggerAppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var available: AvailableEnvironment

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_foo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.replace -> {
                AppDispatcher.dispatch(
                    AppSignal.OpenSession(
                        environment = available.primary()
                    )
                )
                startActivity(Intent(this, FooActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}