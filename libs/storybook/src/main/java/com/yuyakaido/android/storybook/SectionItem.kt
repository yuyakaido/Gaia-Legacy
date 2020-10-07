package com.yuyakaido.android.storybook

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.storybook.databinding.ItemSectionBinding

class SectionItem(
  val section: Item.Section
) : BindableItem<ItemSectionBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_section
  }

  override fun bind(binding: ItemSectionBinding, position: Int) {
    binding.name.text = section.name
  }

}
