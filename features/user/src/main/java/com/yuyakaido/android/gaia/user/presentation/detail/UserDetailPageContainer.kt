package com.yuyakaido.android.gaia.user.presentation.detail

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.entity.User

data class UserDetailPageContainer(
  val title: Int,
  val fragment: (AppNavigatorType, User.Detail) -> Fragment
)