package com.yuyakaido.android.storybook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.storybook.article.list.StorybookFragmentRouter
import com.yuyakaido.android.storybook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    supportFragmentManager
      .beginTransaction()
      .apply {
        replace(
          binding.container.id,
          StorybookFragmentRouter.newInstance()
        )
        commitNow()
      }

  }
}
