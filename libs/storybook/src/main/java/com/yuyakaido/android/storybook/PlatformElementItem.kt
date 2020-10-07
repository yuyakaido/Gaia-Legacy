package com.yuyakaido.android.storybook

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.storybook.databinding.ItemPlatformElementBinding

class PlatformElementItem(
  private val element: Item.Element.Platform
) : BindableItem<ItemPlatformElementBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_platform_element
  }

  override fun bind(binding: ItemPlatformElementBinding, position: Int) {
    binding.name.text = element.name
    binding.container.removeAllViews()
    binding.container.addView(element.view())
  }

}
