package com.yuyakaido.android.gaia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.databinding.ActivityAppBinding
import com.yuyakaido.android.gaia.user.detail.UserDetailSource
import dagger.android.support.DaggerAppCompatActivity

class AppActivity : DaggerAppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, AppActivity::class.java)
        .apply {
          addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
  }

  private val binding by lazy { ActivityAppBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupBottomNavigationView()
  }

  private fun setupBottomNavigationView() {
    val controller = findNavController(R.id.fragment_nav_host)
    val graph = controller.graph
    graph.addArgument(
      "source",
      NavArgumentBuilder()
        .apply {
          type = NavType.ParcelableType(ArticleListSource::class.java)
          nullable = false
          defaultValue = ArticleListSource.Popular
        }
        .build()
    )
    controller.graph = graph

    val view = binding.bottomNavigationView
    view.setupWithNavController(controller)
    view.setOnNavigationItemSelectedListener { item ->
      val direction = when (item.itemId) {
        R.id.fragment_article_list -> {
          NavigationHomeDirections.actionArticleList(source = ArticleListSource.Popular)
        }
        R.id.fragment_search -> {
          NavigationHomeDirections.actionSearch()
        }
        R.id.fragment_community -> {
          NavigationHomeDirections.actionCommunityList()
        }
        R.id.fragment_user_detail -> {
          NavigationHomeDirections.actionUserDetail(source = UserDetailSource.Me)
        }
        else -> {
          NavigationHomeDirections.actionArticleList(source = ArticleListSource.Popular)
        }
      }
      controller.navigate(direction)
      return@setOnNavigationItemSelectedListener true
    }
  }

}