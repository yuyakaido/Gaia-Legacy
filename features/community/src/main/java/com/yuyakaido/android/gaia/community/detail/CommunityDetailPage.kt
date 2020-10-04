package com.yuyakaido.android.gaia.community.detail

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.community.R
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.entity.Community

enum class CommunityDetailPage(
  val title: Int,
  val fragment: (AppNavigatorType, Community.Summary) -> Fragment
) {
  Article(
    title = R.string.community_detail_articles,
    fragment = fun (navigator: AppNavigatorType, community: Community.Summary): Fragment {
      return navigator.newCommunityDetailArticleListFragment(community = community)
    }
  ),
  Moderator(
    title = R.string.community_detail_moderators,
    fragment = fun (navigator: AppNavigatorType, community: Community.Summary): Fragment {
      return navigator.newModeratorListFragment(community = community)
    }
  ),
  Contributor(
    title = R.string.community_detail_contributors,
    fragment = fun (navigator: AppNavigatorType, community: Community.Summary): Fragment {
      return navigator.newContributorListFragment(community = community)
    }
  ),
}