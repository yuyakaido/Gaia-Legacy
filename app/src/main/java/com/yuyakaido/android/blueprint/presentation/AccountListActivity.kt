package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.databinding.ActivityAccountListBinding
import com.yuyakaido.android.blueprint.domain.RunningSession
import com.yuyakaido.android.blueprint.domain.Session
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class AccountListActivity : AppCompatActivity(), AccountItem.OnLogoutListener {

    companion object {
        fun createIntent(context: Context) = Intent(context, AccountListActivity::class.java)
    }

    private val disposables = CompositeDisposable()
    private val section = Section()
    private val binding by lazy { ActivityAccountListBinding.inflate(layoutInflater) }

    @Inject
    lateinit var running: RunningSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Blueprint).component.inject(this)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onLogout(session: Session) {
        running.remove(session)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        title = "Account List"
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = GroupAdapter<ViewHolder>()
                .apply { add(section) }

        running.sessions()
                .subscribe { section.update(it.map { AccountItem(it, this) }) }
                .addTo(disposables)
    }

}