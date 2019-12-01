package com.yuyakaido.android.gaia.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.search.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

  companion object {
    fun newInstance(): SearchFragment {
      return SearchFragment()
    }
  }

  private val viewModel by viewModels<SearchViewModel>()

  private lateinit var binding: FragmentSearchBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentSearchBinding.inflate(inflater)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecyclerView()
    viewModel.onBind()
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.trendingRecyclerView.adapter = adapter

    viewModel
      .trendingSubreddits
      .observe(viewLifecycleOwner) { subreddits ->
        adapter.updateAsync(subreddits.map { trendingSubreddit ->
          TrendingSubredditItem(
            trendingSubreddit = trendingSubreddit
          )
        })
      }
  }

}