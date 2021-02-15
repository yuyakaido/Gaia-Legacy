package com.yuyakaido.android.core.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.core.widget.databinding.ItemArticleBinding
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.entity.Article

class ArticleItem(
  val article: Article,
  private val imageLoader: ImageLoaderType,
  private val upvoteListener: (Article) -> Unit,
  private val downvoteListener: (Article) -> Unit,
  private val communityListener: (Article) -> Unit
) : BindableItem<ItemArticleBinding>() {

  private val placeholder = ColorDrawable(Color.LTGRAY)

  override fun initializeViewBinding(view: View): ItemArticleBinding {
    return ItemArticleBinding.bind(view)
  }

  override fun isSameAs(other: Item<*>): Boolean {
    return if (other is ArticleItem) {
      other.article.id == article.id
    } else {
      false
    }
  }

  override fun hasSameContentAs(other: Item<*>): Boolean {
    return if (other is ArticleItem) {
      other.article == article
    } else {
      false
    }
  }

  override fun getLayout(): Int {
    return R.layout.item_article
  }

  override fun bind(binding: ItemArticleBinding, position: Int) {
    val context = binding.root.context

    binding.article.community.text = context.resources.getString(R.string.article_list_community, article.community.name.value)
    binding.article.author.text = context.resources.getString(R.string.article_list_author, article.author)
    binding.article.title.text = article.title
    binding.article.voteCount.text = article.voteCount.toString()
    binding.article.upvote.setOnClickListener { upvoteListener.invoke(article) }
    binding.article.downvote.setOnClickListener { downvoteListener.invoke(article) }
    binding.article.community.setOnClickListener { communityListener.invoke(article) }
    when (article.likes) {
      null -> {
        binding.article.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.article.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      true -> {
        binding.article.upvote.setImageResource(R.drawable.ic_upvote_active)
        binding.article.downvote.setImageResource(R.drawable.ic_downvote_inactive)
        binding.article.voteCount.setTextColor(
          ContextCompat.getColor(context, R.color.upvpte)
        )
      }
      false -> {
        binding.article.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.article.downvote.setImageResource(R.drawable.ic_downvote_active)
        binding.article.voteCount.setTextColor(
          ContextCompat.getColor(context, R.color.downvote)
        )
      }
    }
    binding.article.commentCount.text = article.comments.toString()
    if (article.thumbnail == Uri.EMPTY) {
      binding.article.thumbnail.setImageDrawable(placeholder)
    } else {
      imageLoader.load(article.thumbnail, placeholder, binding.article.thumbnail)
    }
  }

}