package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.app.GlideApp
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
        GlideApp.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.image)
        holder.name.text = tweet.user.name
        holder.screenName.text = tweet.user.screenName
        holder.text.text = tweet.text
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val image = root.findViewById<ImageView>(R.id.image)
        val name = root.findViewById<TextView>(R.id.name)
        val screenName = root.findViewById<TextView>(R.id.screen_name)
        val text = root.findViewById<TextView>(R.id.text)
    }

}