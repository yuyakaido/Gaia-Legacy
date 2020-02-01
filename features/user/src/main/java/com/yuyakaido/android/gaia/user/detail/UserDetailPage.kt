package com.yuyakaido.android.gaia.user.detail

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.user.R

enum class UserDetailPage {

  Me {
    override val containers = listOf(
      UserDetailPageContainer(
        title = R.string.user_detail_submitted_articles,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newSubmittedArticleListFragment(user = user)
        }
      ),
      UserDetailPageContainer(
        title = R.string.user_detail_comments,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newCommentListFragment(user = user)
        }
      ),
      UserDetailPageContainer(
        title = R.string.user_detail_upvoted_articles,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newUpvotedArticleListFragment(user = user)
        }
      ),
      UserDetailPageContainer(
        title = R.string.user_detail_downvoted_articles,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newDownvotedArticleListFragment(user = user)
        }
      )
    )
  },
  Other {
    override val containers = listOf(
      UserDetailPageContainer(
        title = R.string.user_detail_submitted_articles,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newSubmittedArticleListFragment(user = user)
        }
      ),
      UserDetailPageContainer(
        title = R.string.user_detail_comments,
        fragment = fun (router: AppRouterType, user: User.Detail): Fragment {
          return router.newCommentListFragment(user = user)
        }
      )
    )
  };

  abstract val containers: List<UserDetailPageContainer>

  fun size(): Int {
    return containers.size
  }
  fun title(index: Int): Int {
    return containers[index].title
  }
  fun fragment(router: AppRouterType, user: User.Detail, index: Int): Fragment {
    return containers[index].fragment.invoke(router, user)
  }

}