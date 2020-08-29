package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import javax.inject.Inject

class AppViewModel @Inject constructor(
  application: Application
) : BaseViewModel(application)