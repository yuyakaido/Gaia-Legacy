package com.yuyakaido.android.blueprint.presentation

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.app.GlideApp
import com.yuyakaido.android.blueprint.databinding.ItemTweetBinding
import com.yuyakaido.android.blueprint.domain.Tweet

data class TweetItem(private val tweet: Tweet) : BindableItem<ItemTweetBinding>() {

    override fun getId(): Long {
        return tweet.id
    }

    override fun getLayout(): Int {
        return R.layout.item_tweet
    }

    override fun bind(binding: ItemTweetBinding, position: Int) {
        GlideApp.with(binding.root.context)
                .load(tweet.user.profileImageUrl)
                .into(binding.image)
        binding.name.text = tweet.user.name
        binding.screenName.text = tweet.user.screenName
        binding.text.text = tweet.text
    }

}