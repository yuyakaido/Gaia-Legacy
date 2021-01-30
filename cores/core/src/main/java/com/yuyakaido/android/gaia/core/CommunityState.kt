package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Community

sealed class CommunityState {

  abstract val communities: List<Community.Detail>
  abstract val after: String?
  abstract fun canPaginate(): Boolean

  object Initial: CommunityState() {
    override val communities: List<Community.Detail> = emptyList()
    override val after: String? = null
    override fun canPaginate(): Boolean = true
  }

  data class Loading(
    override val communities: List<Community.Detail>,
    override val after: String?
  ) : CommunityState() {
    override fun canPaginate(): Boolean = false
  }

  data class Ideal(
    override val communities: List<Community.Detail>,
    override val after: String?
  ) : CommunityState() {
    override fun canPaginate(): Boolean = after?.isNotEmpty() ?: false
  }

  object Error : CommunityState() {
    override val communities: List<Community.Detail> = emptyList()
    override val after: String? = null
    override fun canPaginate(): Boolean = false
  }

  fun toInitial(): CommunityState {
    return Initial
  }

  fun toLoading(
    action: CommunityAction.ToLoading
  ): CommunityState {
    return Loading(
      communities = action.communities,
      after = after
    )
  }

  fun toIdeal(
    action: CommunityAction.ToIdeal
  ): CommunityState {
    return Ideal(
      communities = communities.plus(action.communities),
      after = action.after
    )
  }

  fun toError(): CommunityState {
    return Error
  }

}