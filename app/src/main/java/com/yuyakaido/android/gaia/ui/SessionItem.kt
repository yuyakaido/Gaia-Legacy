package com.yuyakaido.android.gaia.ui

import android.view.View
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
        val indicator = holder.itemView.findViewById<View>(R.id.indicator)
        val title = holder.itemView.findViewById<TextView>(R.id.title)
        if (session.isLoggedOut()) {
            when (session.status) {
                Session.Status.Active -> {
                    indicator.setBackgroundResource(R.drawable.session_logged_out_active)
                }
                Session.Status.Inactive -> {
                    indicator.setBackgroundResource(R.drawable.session_logged_out_inactive)
                }
            }
        } else {
            when (session.status) {
                Session.Status.Active -> {
                    indicator.setBackgroundResource(R.drawable.session_logged_in_active)
                }
                Session.Status.Inactive -> {
                    indicator.setBackgroundResource(R.drawable.session_logged_in_inactive)
                }
            }
        }

        title.text = session.toString()
    }

}