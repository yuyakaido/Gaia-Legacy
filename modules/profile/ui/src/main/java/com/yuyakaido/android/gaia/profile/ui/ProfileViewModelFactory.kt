package com.yuyakaido.android.gaia.profile.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.profile.domain.GetUserUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
  private val application: Application,
  private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ProfileViewModel(
      application = application,
      getUserUseCase = getUserUseCase
    ) as T
  }

}