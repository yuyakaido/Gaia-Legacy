package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yuyakaido.android.blueprint.databinding.ItemAccountSpinnerBinding
import com.yuyakaido.android.blueprint.domain.Session

class AccountSpinnerAdapter(
        private val context: Context) : BaseAdapter() {

    private var sessions = listOf<Session>()

    override fun getCount(): Int {
        return sessions.size
    }

    override fun getItemId(position: Int): Long {
        return sessions[position].twitter.id
    }

    override fun getItem(position: Int): Session {
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

    fun replace(sessions: List<Session>) {
        this.sessions = sessions
        notifyDataSetChanged()
    }

    fun indexOf(session: Session): Int {
        return sessions.indexOfFirst { it.twitter.userId == session.twitter.id }
    }

}