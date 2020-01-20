package com.yuyakaido.android.storybook

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.comment.list.CommentListFragment
import com.yuyakaido.android.gaia.core.domain.entity.Article

class StorybookRouter(override val application: Application) : StorybookRouterType {

  override fun newInstance(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun createIntent(
    context: Context
  ): Intent {
    return ArticleDetailActivity.createIntent(
      context = context,
      article = Article.create("0")
    )
  }

  override fun newCommentListFragment(article: Article): Fragment {
    return CommentListFragment.createIntent(article = Article.create("0"))
  }
}