package com.yuyakaido.android.gaia.ui

import android.widget.TextView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.R
import com.yuyakaido.android.gaia.core.java.Session

class SessionItem(val session: Session) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_session
    }

    override fun bind(holder: ViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.title)
        title.text = session.toString()
    }

}