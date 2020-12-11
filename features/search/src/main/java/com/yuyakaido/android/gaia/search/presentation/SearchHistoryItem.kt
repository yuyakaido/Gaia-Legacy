package com.yuyakaido.android.gaia.search.presentation

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory
import com.yuyakaido.android.gaia.search.R
import com.yuyakaido.android.gaia.search.databinding.ItemSearchHistoryBinding

class SearchHistoryItem(
  private val history: SearchHistory
) : BindableItem<ItemSearchHistoryBinding>() {

  override fun initializeViewBinding(view: View): ItemSearchHistoryBinding {
    return ItemSearchHistoryBinding.bind(view)
  }

  override fun isSameAs(other: Item<*>): Boolean {
    return if (other is SearchHistoryItem) {
      other.history.name == history.name
    } else {
      false
    }
  }

  override fun hasSameContentAs(other: Item<*>): Boolean {
    return if (other is SearchHistoryItem) {
      other.history == history
    } else {
      false
    }
  }

  override fun getLayout(): Int {
    return R.layout.item_search_history
  }

  override fun bind(binding: ItemSearchHistoryBinding, position: Int) {
    binding.name.text = history.name
  }

}