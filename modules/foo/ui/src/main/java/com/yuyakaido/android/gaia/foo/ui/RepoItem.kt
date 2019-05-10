package com.yuyakaido.android.gaia.foo.ui

import android.widget.TextView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.core.Repo
import com.yuyakaido.android.gaia.ui.R

class RepoItem(private val repo: Repo) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_repo
    }

    override fun bind(holder: ViewHolder, position: Int) {
        holder.root.findViewById<TextView>(R.id.name).text = repo.name
    }

}