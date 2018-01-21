package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.domain.Tweet

class TweetAdapter(
        private val context: Context,
        private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_tweet, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.text.text = tweet.text
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val text = root.findViewById<TextView>(R.id.text)
    }

}