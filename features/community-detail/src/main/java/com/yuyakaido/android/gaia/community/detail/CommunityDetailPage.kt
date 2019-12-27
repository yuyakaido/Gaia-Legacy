package com.yuyakaido.android.gaia.community.detail

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Community

enum class CommunityDetailPage(
  val title: Int,
  val fragment: (AppRouterType, Community.Summary) -> Fragment
) {
  Article(
    title = R.string.community_articles,
    fragment = fun (router: AppRouterType, community: Community.Summary): Fragment {
      return router.newCommunityDetailArticleListFragment(community = community)
    }
  ),
  Moderator(
    title = R.string.community_moderators,
    fragment = fun (router: AppRouterType, community: Community.Summary): Fragment {
      return router.newUserListFragment(community = community)
    }
  );
}