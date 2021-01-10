package com.yuyakaido.android.gaia

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel

class AppViewModel @ViewModelInject constructor(
  application: Application
) : BaseViewModel(application)