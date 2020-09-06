package com.yuyakaido.android.gaia.community.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yuyakaido.android.gaia.community.databinding.FragmentCommunityListBinding
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class CommunityListFragment : BaseFragment<CommunityListViewModel>() {

  companion object {
    fun newInstance(): Fragment {
      return CommunityListFragment()
    }
  }

  override val viewModel: CommunityListViewModel by viewModels { factory }

  private lateinit var binding: FragmentCommunityListBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCommunityListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    setupRetryButton()
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .build()
    )

    val listener = { community: Community.Detail ->
      val summary = Community.Summary(name = community.name)
      appNavigator.navigateToCommunityDetail(
        controller = findNavController(),
        community = summary
      )
    }

    viewModel.state
      .observe(viewLifecycleOwner) { state ->
        binding.progressBar.visibility = state.progressVisibility
        binding.retryButton.visibility = state.retryVisibility
        adapter.updateAsync(
          state.communities.map { community ->
            CommunityItem(
              community = community,
              listener = listener
            )
          }
        )
      }
  }

  private fun setupRetryButton() {
    binding.retryButton.setOnClickListener {
      viewModel.onRetry()
    }
  }

}