package com.yuyakaido.android.gaia

import android.net.Uri
import com.bumptech.glide.Glide
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.databinding.ItemSubredditBinding

class SubredditItem(
  private val subreddit: Subreddit
) : BindableItem<ItemSubredditBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_subreddit
  }

  override fun bind(binding: ItemSubredditBinding, position: Int) {
    binding.title.text = subreddit.title
    if (subreddit.thumbnail != Uri.EMPTY) {
      Glide
        .with(binding.root.context)
        .load(subreddit.thumbnail)
        .into(binding.thumbnail)
    }
  }

}