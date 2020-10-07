package com.yuyakaido.android.storybook

import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.storybook.databinding.ItemGroupieElementBinding

class GroupieElementItem(
  private val element: Item.Element.Groupie
) : BindableItem<ItemGroupieElementBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_groupie_element
  }

  override fun bind(binding: ItemGroupieElementBinding, position: Int) {
    binding.name.text = element.name
    binding.container.layoutManager = LinearLayoutManager(binding.root.context)
    binding.container.adapter = GroupAdapter<GroupieViewHolder>()
      .apply { add(element.group()) }
  }

}