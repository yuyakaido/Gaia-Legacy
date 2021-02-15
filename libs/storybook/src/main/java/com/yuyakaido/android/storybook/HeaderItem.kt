package com.yuyakaido.android.storybook

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.storybook.databinding.ItemHeaderBinding

class HeaderItem(
  private val type: Type
) : BindableItem<ItemHeaderBinding>() {

  enum class Type {
    History,
    Category
  }

  override fun initializeViewBinding(view: View): ItemHeaderBinding {
    return ItemHeaderBinding.bind(view)
  }

  override fun getLayout(): Int {
    return R.layout.item_header
  }

  override fun bind(binding: ItemHeaderBinding, position: Int) {
    binding.title.text = type.name
  }

}
