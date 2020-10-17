package com.yuyakaido.android.gaia.support

import com.yuyakaido.android.gaia.core.SessionState

data class SessionContent(
  val session: SessionState,
  val isSelected: Boolean
)