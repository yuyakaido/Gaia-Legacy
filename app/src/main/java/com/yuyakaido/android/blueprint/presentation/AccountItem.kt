package com.yuyakaido.android.blueprint.presentation

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.databinding.ItemAccountListBinding
import com.yuyakaido.android.blueprint.domain.Account

data class AccountItem(
        private val account: Account,
        private val listener: OnLogoutListener) : BindableItem<ItemAccountListBinding>() {

    interface OnLogoutListener {
        fun onLogout(account: Account)
    }

    override fun getLayout(): Int {
        return R.layout.item_account_list
    }

    override fun bind(binding: ItemAccountListBinding, position: Int) {
        binding.screenName.text = account.twitter.userName
        binding.logout.setOnClickListener { listener.onLogout(account) }
    }

}