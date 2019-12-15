package com.yuyakaido.android.gaia.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yuyakaido.android.gaia.core.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.misc.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.profile.databinding.FragmentProfileBinding
import timber.log.Timber
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

  companion object {
    fun newInstance(): ProfileFragment {
      return ProfileFragment()
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<ProfileViewModel>

  private val viewModel: ProfileViewModel by activityViewModels { factory }

  private lateinit var binding: FragmentProfileBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentProfileBinding.inflate(inflater)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupProfile()
    Timber.d("fragment = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
    viewModel.onBind()
  }

  private fun setupProfile() {
    viewModel.me
      .observe(viewLifecycleOwner) { me ->
        Glide.with(requireContext())
          .load(me.icon)
          .transform(RoundedCorners(16.dpTpPx(requireContext())))
          .into(binding.icon)
        binding.identity.text = getString(R.string.identity, me.name)
        binding.birthday.text = me.birthday.toString()
        binding.karma.text = getString(R.string.karma, me.karma)
        binding.follower.text = getString(R.string.follower, me.follower)
        setupViewPager(me)
      }
  }

  private fun setupViewPager(me: Me) {
    val adapter = VoteListFragmentPagerAdapter(
      manager = childFragmentManager,
      router = appRouter,
      me = me
    )
    binding.viewPager.adapter = adapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)
  }

}