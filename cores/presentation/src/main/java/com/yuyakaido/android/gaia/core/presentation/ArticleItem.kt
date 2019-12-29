package com.yuyakaido.android.gaia.core.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.presentation.databinding.ItemArticleBinding

class ArticleItem(
  val article: Article,
  private val upvoteListener: (Article) -> Unit,
  private val downvoteListener: (Article) -> Unit,
  private val communityListener: (Article) -> Unit
) : BindableItem<ItemArticleBinding>() {

  private val placeholder = ColorDrawable(Color.LTGRAY)

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is ArticleItem) {
      other.article.id == article.id
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is ArticleItem) {
      other.article == article
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return article.hashCode()
  }

  override fun getLayout(): Int {
    return R.layout.item_article
  }

  override fun bind(binding: ItemArticleBinding, position: Int) {
    val context = binding.root.context

    binding.community.text = article.community.name
    binding.author.text = binding.root.resources.getString(R.string.article_list_author, article.author)
    binding.title.text = article.title
    binding.voteCount.text = article.voteCount.toString()
    binding.upvote.setOnClickListener { upvoteListener.invoke(article) }
    binding.downvote.setOnClickListener { downvoteListener.invoke(article) }
    binding.community.setOnClickListener { communityListener.invoke(article) }
    when {
      article.likes == null -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      article.likes == true -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_active)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
        binding.voteCount.setTextColor(
          ContextCompat.getColor(context, R.color.upvpte)
        )
      }
      article.likes == false -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_active)
        binding.voteCount.setTextColor(
          ContextCompat.getColor(context, R.color.downvote)
        )
      }
      else -> Unit
    }
    binding.commentCount.text = article.comments.toString()
    if (article.thumbnail == Uri.EMPTY) {
      binding.thumbnail.setImageDrawable(placeholder)
    } else {
      Glide
        .with(binding.root.context)
        .load(article.thumbnail)
        .placeholder(placeholder)
        .into(binding.thumbnail)
    }
  }

}