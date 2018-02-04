package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuyakaido.android.blueprint.databinding.ItemAccountSpinnerBinding
import com.yuyakaido.android.blueprint.domain.Account

class AccountSpinnerAdapter(
        private val context: Context) : BaseAdapter() {

    private var accounts = listOf<Account>()

    override fun getCount(): Int {
        return accounts.size
    }

    override fun getItemId(position: Int): Long {
        return accounts[position].twitter.id
    }

    override fun getItem(position: Int): Account {
        return accounts[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemAccountSpinnerBinding.inflate(LayoutInflater.from(context))
                    .apply { root.tag = this }
        } else {
            convertView.tag as ItemAccountSpinnerBinding
        }

        val account = getItem(position)
        binding.name.text = account.twitter.userName

        return binding.root
    }

    fun replace(accounts: List<Account>) {
        this.accounts = accounts
        notifyDataSetChanged()
    }

    fun indexOf(account: Account): Int {
        return accounts.indexOfFirst { it == account }
    }

}