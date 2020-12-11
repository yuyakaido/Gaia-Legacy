package com.yuyakaido.android.storybook

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.storybook.databinding.ItemSectionBinding

class SectionItem(
  val section: Item.Section
) : BindableItem<ItemSectionBinding>() {

  override fun initializeViewBinding(view: View): ItemSectionBinding {
    return ItemSectionBinding.bind(view)
  }

  override fun getLayout(): Int {
    return R.layout.item_section
  }

  override fun bind(binding: ItemSectionBinding, position: Int) {
    binding.name.text = section.name
  }

}
