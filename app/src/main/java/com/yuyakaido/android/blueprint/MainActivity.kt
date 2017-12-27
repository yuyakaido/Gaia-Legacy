package com.yuyakaido.android.blueprint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var client: TwitterApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTwitter()
        setupRecyclerView()
    }

    private fun setupTwitter() {
        TwitterCore.getInstance().addGuestApiClient(client)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.timeline)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
                .setTimeline(UserTimeline.Builder().build())
                .build()
    }

}
