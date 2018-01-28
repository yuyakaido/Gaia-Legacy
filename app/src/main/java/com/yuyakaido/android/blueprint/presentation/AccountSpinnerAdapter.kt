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

    private var sessions = listOf<Account>()

    override fun getCount(): Int {
        return sessions.size
    }

    override fun getItemId(position: Int): Long {
        return sessions[position].twitter.id
    }

    override fun getItem(position: Int): Account {
        return sessions[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemAccountSpinnerBinding.inflate(LayoutInflater.from(context))
                    .apply { root.tag = this }
        } else {
            convertView.tag as ItemAccountSpinnerBinding
        }

        val session = getItem(position)
        binding.name.text = session.twitter.userName

        return binding.root
    }

    fun replace(accounts: List<Account>) {
        this.sessions = accounts
        notifyDataSetChanged()
    }

    fun indexOf(account: Account): Int {
        return sessions.indexOfFirst { it.twitter.userId == account.twitter.id }
    }

}