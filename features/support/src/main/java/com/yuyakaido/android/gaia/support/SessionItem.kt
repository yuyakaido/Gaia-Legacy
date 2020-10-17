package com.yuyakaido.android.gaia.support

import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.support.databinding.ItemSessionBinding

class SessionItem(
  val content: SessionContent
) : BindableItem<ItemSessionBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_session
  }

  override fun bind(binding: ItemSessionBinding, position: Int) {
    val icon = if (content.isSelected) { "🔵" } else { "" }
    binding.name.text = when (val session = content.session) {
      is SessionState.SignedOut -> "$icon${session.id}"
      is SessionState.SigningIn -> "$icon${session.id}"
      is SessionState.SignedIn -> "$icon${session.me.name}"
    }
  }

  override fun isSameAs(other: Item<*>?): Boolean {
    return if (other is SessionItem) {
      other.content == content
    } else {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    return if (other is SessionItem) {
      other.content == content
    } else {
      false
    }
  }

  override fun hashCode(): Int {
    return content.hashCode()
  }

}