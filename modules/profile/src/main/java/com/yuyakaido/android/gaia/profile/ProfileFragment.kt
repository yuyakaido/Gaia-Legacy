package com.yuyakaido.android.gaia.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yuyakaido.android.gaia.profile.databinding.FragmentProfileBinding
import timber.log.Timber

class ProfileFragment : Fragment() {

  companion object {
    fun newInstance(): ProfileFragment {
      return ProfileFragment()
    }
  }

  private val viewModel by viewModels<ProfileViewModel>()

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
    Timber.d("Gaia - ProfileFragment: fragment = ${hashCode()}")
    Timber.d("Gaia - ProfileFragment: viewmodel = ${viewModel.hashCode()}")
    viewModel.onBind()
  }

  private fun setupProfile() {
    viewModel.me
      .observe(viewLifecycleOwner) { me ->
        Glide.with(requireContext())
          .load(me.icon)
          .transform(RoundedCorners(16))
          .into(binding.icon)
        binding.name.text = me.name
      }
  }

}