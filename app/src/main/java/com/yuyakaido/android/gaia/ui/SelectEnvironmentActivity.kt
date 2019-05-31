package com.yuyakaido.android.gaia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.R
import com.yuyakaido.android.gaia.core.android.GatewayIntentResolverType
import com.yuyakaido.android.gaia.core.java.*
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SelectEnvironmentActivity : DaggerAppCompatActivity(), SelectEnvironmentDialog.OnDismissListener {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectEnvironmentActivity::class.java)
        }
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var appStore: AppStore

    @Inject
    lateinit var resolver: GatewayIntentResolverType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_environment)
        setupDialog()
        setupRecyclerView()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_select_environment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.select -> {
                showDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDismiss(environment: Environment) {
        val session = Session.newSession(environment)
        AppDispatcher.dispatch(AppSignal.OpenSession(session))
        startGatewayActivity()
    }

    private fun setupDialog() {
        appStore.single()
            .filter { it.sessions.isEmpty() }
            .subscribeBy { showDialog() }
            .addTo(disposables)
    }

    private fun setupRecyclerView() {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener { item, _ ->
            if (item is SessionItem) {
                val session = item.session
                AppDispatcher.dispatch(AppAction.SelectSession(session))
                startGatewayActivity()
            }
        }
        adapter.setOnItemLongClickListener { item, _ ->
            appStore.single()
                .subscribeBy { state ->
                    if (item is SessionItem && state.sessions.size > 1) {
                        val session = item.session
                        AppDispatcher.dispatch(AppSignal.CloseSession(session))
                    }
                }
                .addTo(disposables)
            return@setOnItemLongClickListener true
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        appStore.observable()
            .subscribeBy { state ->
                adapter.clear()
                adapter.addAll(state.sessions.map { SessionItem(it) })
            }
            .addTo(disposables)
    }

    private fun showDialog() {
        val dialog = SelectEnvironmentDialog.newInstance()
        dialog.show(supportFragmentManager, SelectEnvironmentDialog::class.java.simpleName)
    }

    private fun startGatewayActivity() {
        startActivity(resolver.getGatewayActivityIntent(this))
        finish()
    }

}