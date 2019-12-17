package com.yuyakaido.android.gaia.core.domain.app

import com.yuyakaido.android.gaia.core.domain.value.VoteTarget

interface VoteServiceType {
  suspend fun vote(target: VoteTarget)
}