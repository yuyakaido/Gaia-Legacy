package com.yuyakaido.android.gaia.vote

import com.yuyakaido.android.gaia.core.domain.app.VoteServiceType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository
import javax.inject.Inject

class VoteService @Inject constructor(
  private val repository: ArticleRepository
) : VoteServiceType {

  override suspend fun vote(target: VoteTarget) {
    return repository.vote(target = target)
  }

}