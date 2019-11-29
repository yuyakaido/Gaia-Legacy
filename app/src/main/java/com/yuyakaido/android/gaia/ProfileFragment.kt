package com.yuyakaido.android.gaia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

  companion object {
    fun newInstance(): ProfileFragment {
      return ProfileFragment()
    }
  }

  private lateinit var binding: FragmentProfileBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentProfileBinding.inflate(inflater)
    return binding.root
  }

}