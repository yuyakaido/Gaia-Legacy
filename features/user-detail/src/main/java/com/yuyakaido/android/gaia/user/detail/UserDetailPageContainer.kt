package com.yuyakaido.android.gaia.user.detail

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.User

data class UserDetailPageContainer(
  val title: Int,
  val fragment: (AppRouterType, User.Detail) -> Fragment
)