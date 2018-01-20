package com.yuyakaido.android.blueprint

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import com.twitter.sdk.android.tweetui.UserTimeline

class MainActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<TwitterLoginButton>(R.id.button) }

    private val spinner by lazy { findViewById<Spinner>(R.id.spinner) }
    private val adapter by lazy { TwitterAccountAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupLoginButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                switchTwitter(adapter.getItem(position), false)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupLoginButton() {
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                val session = result.data
                adapter.add(session)
                adapter.notifyDataSetChanged()
                setupApiClient(session)
                switchTwitter(session, true)
            }
            override fun failure(exception: TwitterException) {
                Log.e("Blueprint", exception.toString())
            }
        }
    }

    private fun switchTwitter(session: TwitterSession, forceSwitch: Boolean) {
        if (TwitterCore.getInstance().sessionManager.activeSession != session || forceSwitch) {
            TwitterCore.getInstance().sessionManager.activeSession = session
            spinner.setSelection(adapter.indexOf(session))
            setupRecyclerView()
        }
    }

    private fun setupApiClient(session: TwitterSession) {
        val token = TwitterAuthToken(session.authToken.token, session.authToken.secret)
        val client = TwitterApiClient(TwitterSession(token, session.userId, session.userName))
        TwitterCore.getInstance().addApiClient(session, client)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
                .setTimeline(UserTimeline.Builder().build())
                .build()
    }

}
