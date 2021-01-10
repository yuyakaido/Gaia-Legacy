package com.yuyakaido.android.gaia.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.presentation.BaseActivityWithoutHilt
import com.yuyakaido.android.gaia.support.databinding.ActivitySessionListBinding
import javax.inject.Inject

class SessionListActivity : BaseActivityWithoutHilt<SessionListViewModel>() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, SessionListActivity::class.java)
    }
  }

  @Inject
  internal lateinit var appNavigator: AppNavigatorType

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
        viewModel.onSessionClicked(item.content.session)
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter

    viewModel.state
      .observe(this) { state ->
        val items = state.sessions.mapIndexed { index, session ->
          val content = SessionContent(
            session = session,
            isSelected = state.index == index
          )
          SessionItem(content)
        }
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
    viewModel.navigateToGateway
      .observe(this) {
        startActivity(appNavigator.newGatewayActivity())
      }
    viewModel.navigateToApp
      .observe(this) {
        startActivity(appNavigator.newAppActivity())
      }
  }

}