package com.yuyakaido.android.blueprint.presentation

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
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.domain.RunningSession
import com.yuyakaido.android.blueprint.domain.Session
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<TwitterLoginButton>(R.id.button) }
    private val spinner by lazy { findViewById<Spinner>(R.id.spinner) }
    private val adapter by lazy { TwitterAccountAdapter(this, running) }

    @Inject
    lateinit var running: RunningSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Blueprint).component.inject(this)
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
                val target = running.sessions()[position]
                if (running.current()?.twitter?.id != target.twitter.id) {
                    running.switchTo(position)
                    switchTwitter()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupLoginButton() {
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                val session = Session(result.data, application)
                running.add(session)
                spinner.setSelection(running.sessions().indexOf(session))
                adapter.notifyDataSetChanged()
                switchTwitter()
            }
            override fun failure(exception: TwitterException) {
                Log.e("Blueprint", exception.toString())
            }
        }
    }

    private fun switchTwitter() {
        running.current()?.let { current ->
            current.client.homeTimeline(current.twitter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setupRecyclerView(it) }
        }
    }

    private fun setupRecyclerView(tweets: List<Tweet>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = TweetAdapter(this, tweets)
    }

}
