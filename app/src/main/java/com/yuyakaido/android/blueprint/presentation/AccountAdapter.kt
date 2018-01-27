package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuyakaido.android.blueprint.databinding.ItemTwitterAccountBinding
import com.yuyakaido.android.blueprint.domain.RunningSession
import com.yuyakaido.android.blueprint.domain.Session

class AccountAdapter(
        private val context: Context,
        private val running: RunningSession) : BaseAdapter() {

    override fun getCount(): Int {
        return running.sessions().size
    }

    override fun getItemId(position: Int): Long {
        return running.sessions()[position].twitter.id
    }

    override fun getItem(position: Int): Session {
        return running.sessions()[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            ItemTwitterAccountBinding.inflate(LayoutInflater.from(context))
                    .apply { root.tag = this }
        } else {
            convertView.tag as ItemTwitterAccountBinding
        }

        val session = getItem(position)
        binding.name.text = session.twitter.userName

        return binding.root
    }

}