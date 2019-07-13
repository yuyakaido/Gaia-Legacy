package com.yuyakaido.android.gaia.ui

import android.view.View
import android.widget.TextView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.R
import com.yuyakaido.android.gaia.core.java.SessionState

class SessionItem(val session: SessionState) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_session
    }

    override fun bind(holder: ViewHolder, position: Int) {
        val indicator = holder.itemView.findViewById<View>(R.id.indicator)
        val title = holder.itemView.findViewById<TextView>(R.id.title)
        when (session) {
            is SessionState.Resolving -> {
                indicator.setBackgroundResource(R.drawable.session_resolving)
            }
            is SessionState.Resolved.LoggedOut -> {
                indicator.setBackgroundResource(R.drawable.session_logged_out)
            }
            is SessionState.Resolved.LoggedIn -> {
                indicator.setBackgroundResource(R.drawable.session_logged_in)
            }
        }
        title.text = session.toString()
    }

}