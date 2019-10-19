package com.yuyakaido.android.gaia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.core.java.*
import com.yuyakaido.android.gaia.databinding.ActivitySelectEnvironmentBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SelectEnvironmentActivity : DaggerAppCompatActivity(),
  SelectEnvironmentDialog.OnDismissListener, SessionFooter.AddSessionListener {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, SelectEnvironmentActivity::class.java)
    }
  }

  private val disposables = CompositeDisposable()
  private val binding by lazy { ActivitySelectEnvironmentBinding.inflate(layoutInflater) }

  @Inject
  lateinit var appStore: AppStore

  @Inject
  lateinit var available: AvailableEnvironment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupRecyclerView()
  }

  override fun onDestroy() {
    disposables.dispose()
    super.onDestroy()
  }

  override fun onDismiss(environment: Environment) {
    val session = SessionState.newResolvingSession(environment, available)
    AppDispatcher.dispatch(AppSignal.AddSession(session))
  }

  override fun onAddSession() {
    showDialog()
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<ViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SessionItem) {
        val session = item.session
        AppDispatcher.dispatch(AppSignal.SelectSession(session))
      }
    }
    adapter.setOnItemLongClickListener { item, _ ->
      appStore.single()
        .subscribeBy { state ->
          if (item is SessionItem && state.sessions.size > 1) {
            val session = item.session
            AppDispatcher.dispatch(AppSignal.RemoveSession(session))
          }
        }
        .addTo(disposables)
      return@setOnItemLongClickListener true
    }
    binding.recyclerView.layoutManager = LinearLayoutManager(this)
    binding.recyclerView.adapter = adapter

    appStore.observable()
      .subscribeBy { state ->
        adapter.clear()
        adapter.addAll(state.sessions.map { SessionItem(it) })
        adapter.add(SessionFooter(this))
      }
      .addTo(disposables)
  }

  private fun showDialog() {
    val dialog = SelectEnvironmentDialog.newInstance()
    dialog.show(supportFragmentManager, SelectEnvironmentDialog::class.java.simpleName)
  }

}