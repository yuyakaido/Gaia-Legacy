package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import javax.inject.Inject

class NoopAppRouter @Inject constructor(
  override val application: Application
) : AppRouterType {

  override fun newHomeActivity(): Intent {
    throw UnsupportedOperationException()
  }

  override fun newPopularArticleListFragment(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newSubmittedArticleListFragment(user: User): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newUpvotedArticleListFragment(user: User): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newDownvotedArticleListFragment(user: User): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    throw UnsupportedOperationException()
  }

  override fun newCommentListFragment(user: User): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newCommentListFragment(article: Article): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newCommunityFragment(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newCommunityDetailActivity(community: Community.Summary): Intent {
    throw UnsupportedOperationException()
  }

  override fun newModeratorListFragment(community: Community.Summary): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newContributorListFragment(community: Community.Summary): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newUserDetailActivity(user: User): Intent {
    throw UnsupportedOperationException()
  }

  override fun newUserDetailFragmentForUser(user: User): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newUserDetailFragmentForMe(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newSearchFragment(): Fragment {
    throw UnsupportedOperationException()
  }

}