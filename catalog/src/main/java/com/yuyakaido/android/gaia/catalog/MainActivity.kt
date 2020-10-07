package com.yuyakaido.android.gaia.catalog

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.Group
import com.yuyakaido.android.core.widget.ArticleItem
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.storybook.Item
import com.yuyakaido.android.storybook.Storybook
import com.yuyakaido.android.storybook.StorybookActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val imageLoader = object : ImageLoaderType {
      override fun load(uri: Uri, placeholder: Drawable, view: ImageView) = Unit
    }
    val upvote = { _: Article -> Unit }
    val downvote = { _: Article -> Unit }
    val community = { _: Article -> Unit }

    startActivity(
      StorybookActivity.createIntent(
        context = applicationContext,
        storybook = Storybook(
          name = "Gaia",
          items = listOf(
            Item.Section(
              name = "Article",
              items = createArticles().map { article ->
                object : Item.Element.Groupie(name = "Article") {
                  override fun group(): Group {
                    return ArticleItem(
                      article = article,
                      imageLoader = imageLoader,
                      upvoteListener = upvote,
                      downvoteListener = downvote,
                      communityListener = community
                    )
                  }
                }
              }
            )
          )
        )
      )
    )
  }

  private fun createArticles(): List<Article> {
    return listOf(
      Article(
        id = Article.ID(value = "id"),
        name = "name",
        community = Community.Summary(name = "community"),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = false,
        ups = 256,
        downs = 256,
        comments = 256
      )
    )
  }

}
