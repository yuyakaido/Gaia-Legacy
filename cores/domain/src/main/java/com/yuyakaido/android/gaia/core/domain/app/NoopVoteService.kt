package com.yuyakaido.android.gaia.core.domain.app

import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject

class NoopVoteService @Inject constructor() : VoteServiceType {

  override suspend fun vote(target: VoteTarget) {
    throw UnsupportedOperationException()
  }

}