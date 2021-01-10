package com.yuyakaido.android.gaia.community.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yuyakaido.android.gaia.community.R
import com.yuyakaido.android.gaia.community.databinding.FragmentCommunityDetailBinding
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.presentation.BaseFragmentWithoutHilt
import javax.inject.Inject

class CommunityDetailFragment : BaseFragmentWithoutHilt<CommunityDetailViewModel>() {

  @Inject
  internal lateinit var appNavigator: AppNavigatorType

  override val viewModel: CommunityDetailViewModel by viewModels { factory }
  internal val args: CommunityDetailFragmentArgs by navArgs()
  private lateinit var binding: FragmentCommunityDetailBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentCommunityDetailBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupDetail()
    setupViewPager()
  }

  private fun setupDetail() {
    viewModel.state
      .observe(viewLifecycleOwner) { state ->
        binding.contentContainer.visibility = state.contentVisibility
        binding.progressBar.visibility = state.progressVisibility
        when (state) {
          is CommunityDetailViewModel.State.Initial,
          is CommunityDetailViewModel.State.Loading,
          is CommunityDetailViewModel.State.Error -> Unit
          is CommunityDetailViewModel.State.Ideal -> {
            val community = state.community
            Glide.with(this)
              .load(community.banner)
              .into(binding.banner)
            Glide.with(this)
              .load(community.icon)
              .circleCrop()
              .into(binding.icon)
            binding.name.text = getString(R.string.community_detail_name, community.name)
            binding.subscribe.setOnClickListener { viewModel.onSubscribe() }
            if (community.isSubscriber) {
              binding.subscribe.text = getString(R.string.community_detail_unsubscribe)
            } else {
              binding.subscribe.text = getString(R.string.community_detail_subscribe)
            }
            binding.subscribers.text = getString(R.string.community_detail_subscribers, community.subscribers)
            binding.description.text = community.description
          }
        }
      }
  }

  private fun setupViewPager() {
    val adapter = CommunityDetailFragmentPagerAdapter(
      manager = childFragmentManager,
      context = requireContext(),
      navigator = appNavigator,
      community = args.community.toSummary()
    )
    binding.viewPager.adapter = adapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)
  }

}