package com.yuyakaido.android.gaia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val viewModel by viewModels<SearchSubredditViewModel>()
  private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupRecyclerView()
    viewModel.onBind()
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SubredditItem) {
        startActivity(SubredditActivity.createIntent(this@MainActivity, item.subreddit))
      }
    }
    binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    binding.recyclerView.adapter = adapter

    viewModel.subreddits
      .observe(this) { subreddits ->
        adapter.updateAsync(subreddits.map { subreddit -> SubredditItem(subreddit = subreddit) })
      }
  }

}
