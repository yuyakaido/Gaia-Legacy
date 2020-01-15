package com.yuyakaido.android.gaia.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.presentation.ArticleItem
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.search.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

  companion object {
    fun newInstance(): SearchFragment {
      return SearchFragment()
    }
  }

  @Inject
  internal lateinit var factory: ViewModelFactory<SearchViewModel>

  private val viewModel: SearchViewModel by viewModels { factory }

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
    setupSearchView()
    setupTrendingRecyclerView()
    setupSearchedRecyclerView()
    viewModel.onBind()
  }

  private fun setupSearchView() {
    binding.searchView
      .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean = true
        override fun onQueryTextSubmit(query: String?): Boolean {
          query?.let {
            viewModel.onSubmit(query = query)
          }
          return true
        }
      })
    binding.searchView.onActionViewExpanded()
  }

  private fun setupTrendingRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.trendingRecyclerView.adapter = adapter

    viewModel.trendingArticles
      .observe(viewLifecycleOwner) { articles ->
        adapter.updateAsync(articles.map { article ->
          TrendingArticleItem(
            article = article
          )
        })
      }
  }

  private fun setupSearchedRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.searchedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.searchedRecyclerView.adapter = adapter

    val upvoteListener = { _: Article -> Unit }
    val downvoteListener = { _: Article -> Unit }
    val communityListener = { _: Article -> Unit }

    viewModel.searchedArticles
      .observe(viewLifecycleOwner) { articles ->
        adapter.updateAsync(articles.map { article ->
          ArticleItem(
            article = article,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener,
            communityListener = communityListener
          )
        })
      }
  }

}