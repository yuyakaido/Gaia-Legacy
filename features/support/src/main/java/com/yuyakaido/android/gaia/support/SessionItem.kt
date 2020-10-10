package com.yuyakaido.android.gaia.support

import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.support.databinding.ItemSessionBinding

class SessionItem(
  val session: SessionState
) : BindableItem<ItemSessionBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_session
  }

  override fun bind(binding: ItemSessionBinding, position: Int) {
    binding.name.text = session.id()
  }

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is SessionItem) {
      other.session.id() == session.id()
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is SessionItem) {
      other.session == session
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return session.hashCode()
  }

}