package com.yuyakaido.android.gaia.core.domain.value

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Me

enum class VoteListPage(
  val path: String,
  val fragment: (AppRouterType, Me) -> Fragment
) {
  Upvoted(
    path = "upvoted",
    fragment = fun (router: AppRouterType, me: Me): Fragment {
      return router.newVoteListFragment(me = me, page = Upvoted)
    }
  ),
  Downvoted(
    path = "downvoted",
    fragment = fun (router: AppRouterType, me: Me): Fragment {
      return router.newVoteListFragment(me = me, page = Downvoted)
    }
  )
}