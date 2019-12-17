package com.yuyakaido.android.gaia.vote

import com.yuyakaido.android.gaia.core.domain.app.VoteServiceType
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject

class VoteService @Inject constructor(
  private val repository: ArticleRepositoryType
) : VoteServiceType {

  override suspend fun vote(target: VoteTarget) {
    return repository.vote(target = target)
  }

}