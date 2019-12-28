package com.yuyakaido.android.gaia.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.domain.value.UserDetailPage
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.user.detail.databinding.FragmentUserDetailBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class UserDetailFragment : DaggerFragment() {

  companion object {
    private val PAGE = UserDetailPage::class.java.simpleName

    fun newInstance(page: UserDetailPage): UserDetailFragment {
      return UserDetailFragment()
        .apply { arguments = bundleOf(PAGE to page) }
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<UserDetailViewModel>

  private val viewModel: UserDetailViewModel by viewModels { factory }

  private lateinit var binding: FragmentUserDetailBinding

  internal fun getPage(): UserDetailPage {
    return requireNotNull(requireArguments().getParcelable(PAGE))
  }

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
    viewModel.onBind()
  }

  private fun setupProfile() {
    viewModel.detail
      .observe(viewLifecycleOwner) { detail ->
        Glide.with(requireContext())
          .load(detail.icon)
          .transform(RoundedCorners(16.dpTpPx(requireContext())))
          .into(binding.icon)
        binding.identity.text = getString(R.string.identity, detail.name)
        binding.birthday.text = detail.birthday.toString()
        binding.karma.text = getString(R.string.karma, detail.karma)
        setupViewPager(detail)
      }
  }

  private fun setupViewPager(detail: User.Detail) {
    val adapter = VoteListFragmentPagerAdapter(
      manager = childFragmentManager,
      router = appRouter,
      detail = detail
    )
    binding.viewPager.adapter = adapter
    binding.tabLayout.setupWithViewPager(binding.viewPager)
  }

}