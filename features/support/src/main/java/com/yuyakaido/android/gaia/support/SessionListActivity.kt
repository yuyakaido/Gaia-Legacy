package com.yuyakaido.android.gaia.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import com.yuyakaido.android.gaia.support.databinding.ActivitySessionListBinding

class SessionListActivity : BaseActivity<SessionListViewModel>() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, SessionListActivity::class.java)
    }
  }

  override val viewModel: SessionListViewModel by viewModels { factory }
  private val binding by lazy { ActivitySessionListBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupRecyclerView()
    setupSessionButton()
    setupNavigation()
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(this)
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SessionItem) {
        viewModel.onSessionClicked(item.session)
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter

    viewModel.sessions
      .observe(this) { sessions ->
        val items = sessions.map { session -> SessionItem(session) }
        adapter.updateAsync(items)
      }
  }

  private fun setupSessionButton() {
    binding.addSessionButton.setOnClickListener {
      viewModel.onAddSessionClicked()
    }
  }

  private fun setupNavigation() {
    viewModel.navigateToAuth
      .observe(this) {
        startActivity(appNavigator.newAuthActivity(it))
      }
  }

}