package com.yuyakaido.android.storybooks.article.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.storybooks.article.list.databinding.ActivityArticleListStoryBookBinding

class ArticleListStoryBookActivity : AppCompatActivity() {

  private val binding by lazy {
    DataBindingUtil.setContentView<ActivityArticleListStoryBookBinding>(
      this,
      R.layout.activity_article_list_story_book
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportFragmentManager
      .beginTransaction()
      .replace(
        binding.container.id,
        ArticleListFragment.newInstance(source = ArticleListSource.Popular)
      )
      .commitNow()
  }
}
