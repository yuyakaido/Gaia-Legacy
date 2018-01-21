package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.R

class TwitterAccountAdapter(
        private val context: Context) : BaseAdapter() {

    private val sessions = mutableListOf<TwitterSession>()

    override fun getCount(): Int {
        return sessions.size
    }

    override fun getItemId(position: Int): Long {
        return sessions[position].id
    }

    override fun getItem(position: Int): TwitterSession {
        return sessions[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = if (convertView == null) {
            ViewHolder(View.inflate(context, R.layout.item_twitter_account, null))
                    .apply { root.tag = this }
        } else {
            convertView.tag as ViewHolder
        }

        val session = getItem(position)
        holder.name.text = session.userName

        return holder.root
    }

    fun add(session: TwitterSession) {
        sessions.add(session)
    }

    fun indexOf(session: TwitterSession): Int {
        return sessions.indexOf(session)
    }

    private class ViewHolder(val root: View) {
        val name = root as TextView
    }

}