package com.yuyakaido.android.gaia.profile

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Me

enum class VoteListPage(
  val path: String,
  val fragment: (Me) -> Fragment
) {
  Upvoted(
    path = "upvoted",
    fragment = fun (me: Me) = VoteListFragment.newInstance(me = me, page = Upvoted)
  ),
  Downvoted(
    path = "downvoted",
    fragment = fun (me: Me) = VoteListFragment.newInstance(me = me, page = Downvoted)
  )
}