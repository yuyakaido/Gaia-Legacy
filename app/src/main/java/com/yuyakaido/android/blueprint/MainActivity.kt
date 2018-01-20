package com.yuyakaido.android.blueprint

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.UserTimeline

class MainActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<TwitterLoginButton>(R.id.login) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLoginButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupLoginButton() {
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                setupTwitter(TwitterAccount.from(result.data))
            }
            override fun failure(exception: TwitterException) {
                Log.e("Blueprint", exception.toString())
            }
        }
    }

    private fun setupTwitter(account: TwitterAccount) {
        loginButton.visibility = View.GONE
        setupApiClient(account)
        setupRecyclerView()
    }

    private fun setupApiClient(account: TwitterAccount) {
        val token = TwitterAuthToken(account.token, account.secret)
        val client = TwitterApiClient(TwitterSession(token, account.id, account.name))
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
