package com.yuyakaido.android.gaia.search

import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.value.TrendingArticle
import com.yuyakaido.android.gaia.search.databinding.ItemTrendingArticleBinding

class TrendingArticleItem(
  private val article: TrendingArticle
) : BindableItem<ItemTrendingArticleBinding>() {

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is TrendingArticleItem) {
      other.article.name == article.name
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is TrendingArticleItem) {
      other.article == article
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return article.hashCode()
  }

  override fun getLayout(): Int {
    return R.layout.item_trending_article
  }

  override fun bind(binding: ItemTrendingArticleBinding, position: Int) {
    binding.name.text = article.name
  }

}