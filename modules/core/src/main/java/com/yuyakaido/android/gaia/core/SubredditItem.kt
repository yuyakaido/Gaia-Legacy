package com.yuyakaido.android.gaia.core

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.databinding.ItemSubredditBinding

class SubredditItem(
  val subreddit: Subreddit,
  private val upvoteListener: (Subreddit) -> Unit,
  private val downvoteListener: (Subreddit) -> Unit
) : BindableItem<ItemSubredditBinding>() {

  private val placeholder = ColorDrawable(Color.LTGRAY)

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is SubredditItem) {
      other.subreddit.id == subreddit.id
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is SubredditItem) {
      other.subreddit == subreddit
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return subreddit.hashCode()
  }

  override fun getLayout(): Int {
    return R.layout.item_subreddit
  }

  override fun bind(binding: ItemSubredditBinding, position: Int) {
    binding.category.text = subreddit.category
    binding.author.text = binding.root.resources.getString(R.string.author, subreddit.author)
    binding.title.text = subreddit.title
    binding.voteCount.text = subreddit.voteCount.toString()
    binding.upvote.setOnClickListener { upvoteListener.invoke(subreddit) }
    binding.downvote.setOnClickListener { downvoteListener.invoke(subreddit) }
    when {
      subreddit.likes == null -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      subreddit.likes == true -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_active)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      subreddit.likes == false -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_active)
      }
      else -> Unit
    }
    binding.commentCount.text = subreddit.comments.toString()
    if (subreddit.thumbnail == Uri.EMPTY) {
      binding.thumbnail.setImageDrawable(placeholder)
    } else {
      Glide
        .with(binding.root.context)
        .load(subreddit.thumbnail)
        .placeholder(placeholder)
        .into(binding.thumbnail)
    }
  }

}