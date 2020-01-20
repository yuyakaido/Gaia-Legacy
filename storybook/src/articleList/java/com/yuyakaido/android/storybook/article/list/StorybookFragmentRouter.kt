package com.yuyakaido.android.storybook.article.list

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListSource

object StorybookFragmentRouter {

  fun newInstance(): Fragment {
    return ArticleListFragment.newInstance(source = ArticleListSource.Popular)
  }
}
