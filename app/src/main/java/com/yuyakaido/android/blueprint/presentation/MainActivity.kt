package com.yuyakaido.android.blueprint.presentation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.blueprint.R
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.databinding.ActivityMainBinding
import com.yuyakaido.android.blueprint.domain.RunningSession
import com.yuyakaido.android.blueprint.domain.Session
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val twitterAuthClient = TwitterAuthClient()
    private val section = Section()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { AccountAdapter(this, running) }

    @Inject
    lateinit var running: RunningSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Blueprint).component.inject(this)
        setContentView(binding.root)
        setupToolbar()
        setupSwipeRefreshLayout()
        setupRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitterAuthClient.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_account -> authorize()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val target = running.sessions()[position]
                if (running.current()?.twitter?.id != target.twitter.id) {
                    running.switchTo(position)
                    refresh()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            running.current()?.repository?.clearCache()
            refresh()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = GroupAdapter<ViewHolder>()
                .apply { add(section) }
    }

    private fun authorize() {
        twitterAuthClient.authorize(this, object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                val session = Session(result.data, application)
                running.add(session)
                binding.spinner.setSelection(running.sessions().indexOf(session))
                adapter.notifyDataSetChanged()
                refresh()
            }
            override fun failure(exception: TwitterException) {}
        })
    }

    private fun refresh() {
        running.current()?.let { current ->
            current.repository.getHomeTimeline(current.twitter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { binding.swipeRefreshLayout.isRefreshing = false }
                    .subscribe { refreshRecyclerView(it) }
                    .addTo(current.disposables)
        }
    }

    private fun refreshRecyclerView(tweets: List<Tweet>) {
        section.update(tweets.map { TweetItem(it) })
    }

}
