package com.yuyakaido.android.gaia.user.presentation.list

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.user.R
import com.yuyakaido.android.gaia.user.databinding.ItemUserBinding

class UserItem(
  val user: User
) : BindableItem<ItemUserBinding>() {

  override fun initializeViewBinding(view: View): ItemUserBinding {
    return ItemUserBinding.bind(view)
  }

  override fun getLayout(): Int {
    return R.layout.item_user
  }

  override fun bind(binding: ItemUserBinding, position: Int) {
    binding.name.text = user.name
  }

}