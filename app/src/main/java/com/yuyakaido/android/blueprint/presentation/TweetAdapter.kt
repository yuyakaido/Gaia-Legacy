package com.yuyakaido.android.blueprint.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yuyakaido.android.blueprint.app.GlideApp
import com.yuyakaido.android.blueprint.databinding.ItemTweetBinding
import com.yuyakaido.android.blueprint.domain.Tweet

class TweetAdapter(
        private val context: Context,
        private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(ItemTweetBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        GlideApp.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.binding.image)
        holder.binding.name.text = tweet.user.name
        holder.binding.screenName.text = tweet.user.screenName
        holder.binding.text.text = tweet.text
    }

    inner class ViewHolder(val binding: ItemTweetBinding) : RecyclerView.ViewHolder(binding.root)

}