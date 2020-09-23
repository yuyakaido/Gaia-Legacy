package com.yuyakaido.android.gaia.user.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.extension.dpToPx
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import com.yuyakaido.android.gaia.user.R
import com.yuyakaido.android.gaia.user.databinding.FragmentUserDetailBinding
import timber.log.Timber

class UserDetailFragment : BaseFragment<UserDetailViewModel>() {

  companion object {
    fun newInstance(args: UserDetailFragmentArgs): Fragment {
      return UserDetailFragment()
        .apply {
          arguments = args.toBundle()
        }
    }
  }

  override val viewModel: UserDetailViewModel by viewModels { factory }
  internal val args: UserDetailFragmentArgs by navArgs()

  private lateinit var binding: FragmentUserDetailBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentUserDetailBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupProfile()
    Timber.d("fragment = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
  }

  private fun setupProfile() {
    viewModel.detail
      .observe(viewLifecycleOwner) { detail ->
        Glide.with(requireContext())
          .load(detail.icon)
          .transform(RoundedCorners(16.dpToPx(requireContext())))
          .into(binding.icon)
        binding.identity.text = getString(R.string.user_detail_identity, detail.name)
        binding.birthday.text = detail.birthday.toString()
        binding.karma.text = getString(R.string.user_detail_karma, detail.karma)
        setupViewPager(detail)
      }
  }

  private fun setupViewPager(user: User.Detail) {
    val adapter = UserDetailFragmentPagerAdapter(
      manager = childFragmentManager,
      context = requireContext(),
      page = args.source.page(),
      navigator = appNavigator,
      user = user
    )
    binding.viewPager.adapter = adapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)
  }

}