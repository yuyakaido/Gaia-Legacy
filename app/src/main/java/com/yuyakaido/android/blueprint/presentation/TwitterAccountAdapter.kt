package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.domain.RunningSession
import com.yuyakaido.android.blueprint.domain.Session

class TwitterAccountAdapter(
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
        val holder = if (convertView == null) {
            ViewHolder(View.inflate(context, R.layout.item_twitter_account, null))
                    .apply { root.tag = this }
        } else {
            convertView.tag as ViewHolder
        }

        val session = getItem(position)
        holder.name.text = session.twitter.userName

        return holder.root
    }

    private class ViewHolder(val root: View) {
        val name = root as TextView
    }

}