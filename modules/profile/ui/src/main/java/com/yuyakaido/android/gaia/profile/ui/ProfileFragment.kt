package com.yuyakaido.android.gaia.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.profile.ui.databinding.FragmentProfileBinding
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

  companion object {
    fun newInstance(): Fragment {
      return ProfileFragment()
    }
  }

  private val disposables = CompositeDisposable()
  private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

  @Inject
  lateinit var profileViewModel: ProfileViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupUser()
  }

  override fun onDestroyView() {
    disposables.dispose()
    super.onDestroyView()
  }

  private fun setupUser() {
    profileViewModel.user
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeBy { user ->
        binding.name.text = user.login
      }
      .addTo(disposables)
  }

}