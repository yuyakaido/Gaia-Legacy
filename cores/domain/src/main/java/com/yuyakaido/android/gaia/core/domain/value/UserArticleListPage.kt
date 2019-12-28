package com.yuyakaido.android.gaia.core.domain.value

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.User

enum class UserArticleListPage(
  val path: String,
  val fragment: (AppRouterType, User.Detail) -> Fragment
) {
  Submitted(
    path = "submitted",
    fragment = fun (router: AppRouterType, user: User): Fragment {
      return router.newSubmittedArticleListFragment(user = user)
    }
  ),
  Upvoted(
    path = "upvoted",
    fragment = fun (router: AppRouterType, user: User): Fragment {
      return router.newUpvotedArticleListFragment(user = user)
    }
  ),
  Downvoted(
    path = "downvoted",
    fragment = fun (router: AppRouterType, user: User): Fragment {
      return router.newDownvotedArticleListFragment(user = user)
    }
  )
}