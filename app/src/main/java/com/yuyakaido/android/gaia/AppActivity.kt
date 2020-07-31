package com.yuyakaido.android.gaia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.databinding.ActivityAppBinding
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailSource
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
    setupNavigation()
    setupActionBar()
    setupBottomNavigationView()
  }

  // https://developer.android.com/guide/navigation/navigation-ui#action_bar
  override fun onSupportNavigateUp(): Boolean {
    val controller = findNavController()
    return controller.navigateUp() || super.onSupportNavigateUp()
  }

  private fun findNavController(): NavController {
    return findNavController(R.id.fragment_nav_host)
  }

  private fun setupNavigation() {
    val controller = findNavController()
    controller.addOnDestinationChangedListener { _, destination, _ ->
      // https://developer.android.com/guide/navigation/navigation-ui#bottom_navigation
      binding.bottomNavigationView.visibility = TopLevelScreen.visibility(destination.id)
    }

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
  }

  private fun setupActionBar() {
    val configuration = AppBarConfiguration(
      setOf(
        R.id.fragment_article_list,
        R.id.fragment_search,
        R.id.fragment_community_list,
        R.id.fragment_user_detail
      )
    )
    setupActionBarWithNavController(findNavController(), configuration)
  }

  private fun setupBottomNavigationView() {
    val controller = findNavController()
    val view = binding.bottomNavigationView
    view.setupWithNavController(controller)
    view.setOnNavigationItemSelectedListener { item ->
      val direction = when (TopLevelScreen.from(item.itemId)) {
        TopLevelScreen.Article -> {
          NavigationHomeDirections.actionArticleList(source = ArticleListSource.Popular)
        }
        TopLevelScreen.Search -> {
          NavigationHomeDirections.actionSearch()
        }
        TopLevelScreen.Community -> {
          NavigationHomeDirections.actionCommunityList()
        }
        TopLevelScreen.Profile -> {
          NavigationHomeDirections.actionUserDetail(source = UserDetailSource.Me)
        }
      }
      controller.navigate(direction)
      return@setOnNavigationItemSelectedListener true
    }
  }

}