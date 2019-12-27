package com.yuyakaido.android.gaia.user.list

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.user.list.databinding.ItemUserBinding

class UserItem(
  val user: User
) : BindableItem<ItemUserBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_user
  }

  override fun bind(binding: ItemUserBinding, position: Int) {
    binding.name.text = user.name
  }

}