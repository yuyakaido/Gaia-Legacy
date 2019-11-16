package com.yuyakaido.android.gaia

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
  }

}