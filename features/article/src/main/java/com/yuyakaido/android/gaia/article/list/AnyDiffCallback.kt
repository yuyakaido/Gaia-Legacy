package com.yuyakaido.android.gaia.article.list

import androidx.recyclerview.widget.DiffUtil
import com.xwray.groupie.Item

class AnyDiffCallback : DiffUtil.ItemCallback<Item<*>>() {

  override fun areItemsTheSame(
    oldItem: Item<*>,
    newItem: Item<*>
  ): Boolean {
    return oldItem.isSameAs(newItem)
  }

  override fun areContentsTheSame(
    oldItem: Item<*>,
    newItem: Item<*>
  ): Boolean {
    return oldItem.equals(newItem)
  }

}