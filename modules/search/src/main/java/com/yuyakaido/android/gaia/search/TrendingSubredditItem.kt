package com.yuyakaido.android.gaia.search

import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.TrendingSubreddit
import com.yuyakaido.android.gaia.search.databinding.ItemTrendingSubredditBinding

class TrendingSubredditItem(
  private val trendingSubreddit: TrendingSubreddit
) : BindableItem<ItemTrendingSubredditBinding>() {

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is TrendingSubredditItem) {
      other.trendingSubreddit.name == trendingSubreddit.name
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is TrendingSubredditItem) {
      other.trendingSubreddit == trendingSubreddit
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return trendingSubreddit.hashCode()
  }

  override fun getLayout(): Int {
    return R.layout.item_trending_subreddit
  }

  override fun bind(binding: ItemTrendingSubredditBinding, position: Int) {
    binding.name.text = trendingSubreddit.name
  }

}