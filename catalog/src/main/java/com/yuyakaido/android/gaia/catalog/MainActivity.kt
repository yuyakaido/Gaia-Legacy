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
    val upvote = { _: Article -> }
    val downvote = { _: Article -> }
    val community = { _: Article -> }

    startActivity(
      StorybookActivity.createIntent(
        context = applicationContext,
        storybook = Storybook(
          name = "Storybook",
          items = listOf(
            Item.Section(
              name = "Article 1",
              items = createArticles().map { article ->
                object : Item.Element.Groupie(name = article.name) {
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
            ),
            Item.Section(
              name = "Article 2",
              items = createArticles().map { article ->
                object : Item.Element.Groupie(name = article.name) {
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
            ),
            Item.Section(
              name = "Article 3",
              items = createArticles().map { article ->
                object : Item.Element.Groupie(name = article.name) {
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
            ),
            Item.Section(
              name = "Article 4",
              items = createArticles().map { article ->
                object : Item.Element.Groupie(name = article.name) {
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

    finish()
  }

  private fun createArticles(): List<Article> {
    return listOf(
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Normal article",
        community = Community.Summary(name = Community.Name("community")),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = null,
        ups = 0,
        downs = 0,
        comments = 0
      ),
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Upvoted article",
        community = Community.Summary(name = Community.Name("community")),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = true,
        ups = 0,
        downs = 0,
        comments = 0
      ),
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Downvoted article",
        community = Community.Summary(name = Community.Name("community")),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = false,
        ups = 0,
        downs = 0,
        comments = 0
      ),
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Article with very long title",
        community = Community.Summary(name = Community.Name("community")),
        title = "Article with very loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooog title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = false,
        ups = 0,
        downs = 0,
        comments = 0
      ),
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Article with very long community",
        community = Community.Summary(name = Community.Name("looooooooooong community")),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "yuyakaido",
        likes = false,
        ups = 0,
        downs = 0,
        comments = 0
      ),
      Article(
        id = Article.ID(value = System.nanoTime().toString()),
        name = "Article with very long author",
        community = Community.Summary(name = Community.Name("community")),
        title = "title",
        thumbnail = Uri.EMPTY,
        author = "looooooooooong author",
        likes = false,
        ups = 0,
        downs = 0,
        comments = 0
      )
    )
  }

}
