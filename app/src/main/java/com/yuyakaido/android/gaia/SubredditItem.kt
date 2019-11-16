package com.yuyakaido.android.gaia

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.databinding.ItemSubredditBinding

class SubredditItem(
  private val subreddit: Subreddit
) : BindableItem<ItemSubredditBinding>() {

  private val placeholder = ColorDrawable(Color.LTGRAY)

  override fun getLayout(): Int {
    return R.layout.item_subreddit
  }

  override fun bind(binding: ItemSubredditBinding, position: Int) {
    binding.title.text = subreddit.title
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