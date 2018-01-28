package com.yuyakaido.android.blueprint.presentation

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.databinding.ItemAccountListBinding
import com.yuyakaido.android.blueprint.domain.Session

data class AccountItem(
        private val session: Session,
        private val listener: OnLogoutListener) : BindableItem<ItemAccountListBinding>() {

    interface OnLogoutListener {
        fun onLogout(session: Session)
    }

    override fun getLayout(): Int {
        return R.layout.item_account_list
    }

    override fun bind(binding: ItemAccountListBinding, position: Int) {
        binding.screenName.text = session.twitter.userName
        binding.logout.setOnClickListener { listener.onLogout(session) }
    }

}