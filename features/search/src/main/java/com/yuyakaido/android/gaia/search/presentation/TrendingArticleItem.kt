package com.yuyakaido.android.gaia.search.presentation

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle
import com.yuyakaido.android.gaia.search.R
import com.yuyakaido.android.gaia.search.databinding.ItemTrendingArticleBinding

class TrendingArticleItem(
  private val article: TrendingArticle
) : BindableItem<ItemTrendingArticleBinding>() {

  override fun initializeViewBinding(view: View): ItemTrendingArticleBinding {
    return ItemTrendingArticleBinding.bind(view)
  }

  override fun isSameAs(other: Item<*>): Boolean {
    return if (other is TrendingArticleItem) {
      other.article.name == article.name
    } else {
      false
    }
  }

  override fun hasSameContentAs(other: Item<*>): Boolean {
    return if (other is TrendingArticleItem) {
      other.article == article
    } else {
      false
    }
  }

  override fun getLayout(): Int {
    return R.layout.item_trending_article
  }

  override fun bind(binding: ItemTrendingArticleBinding, position: Int) {
    binding.name.text = article.name
  }

}