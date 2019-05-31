package com.yuyakaido.android.gaia.ui

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.R

class SessionFooter(private val listener: AddSessionListener) : Item<ViewHolder>() {

    interface AddSessionListener {
        fun onAddSession()
    }

    override fun getLayout(): Int {
        return R.layout.footer_session
    }

    override fun bind(holder: ViewHolder, position: Int) {
        holder.root.setOnClickListener { listener.onAddSession() }
    }

}