package com.yuyakaido.android.gaia.profile.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.profile.domain.GetUserUseCase

class ProfileViewModel(
  application: Application,
  private val getUserUseCase: GetUserUseCase
) : AndroidViewModel(application) {

  val user = getUserUseCase.getUser()

}