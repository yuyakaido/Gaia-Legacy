package com.yuyakaido.android.gaia.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.core.widget.ArticleItem
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.presentation.BaseFragmentWithHilt
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragmentWithHilt() {

  companion object {
    fun newInstance(): SearchFragment {
      return SearchFragment()
    }
  }

  @Inject
  internal lateinit var imageLoader: ImageLoaderType

  private val viewModel: SearchViewModel by viewModels()
  private lateinit var binding: FragmentSearchBinding

  override fun getViewModel(): BaseViewModel {
    return viewModel
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSearchBinding.inflate(inflater)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupSearchView()
    setupTrendingRecyclerView()
    setupHistoryRecyclerView()
    setupSearchedRecyclerView()
  }

  override fun onResume() {
    super.onResume()
    binding.searchView.clearFocus()
  }

  override fun onPause() {
    super.onPause()
    binding.searchView.clearFocus()
  }

  private fun setupSearchView() {
    binding.searchView
      .setOnSearchClickListener {
        viewModel.onFocus()
      }
    binding.searchView
      .setOnCloseListener {
        viewModel.onClose()
        return@setOnCloseListener false
      }
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
  }

  private fun setupTrendingRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.trendingRecyclerView.adapter = adapter

    viewModel.trendingArticles
      .observe(viewLifecycleOwner) { articles ->
        if (articles.isEmpty()) {
          binding.trendingRecyclerView.visibility = View.INVISIBLE
          binding.historyRecyclerView.visibility = View.INVISIBLE
        } else {
          binding.trendingRecyclerView.visibility = View.VISIBLE
          binding.historyRecyclerView.visibility = View.INVISIBLE
          adapter.updateAsync(articles.map { article ->
            TrendingArticleItem(
              article = article
            )
          })
        }
      }
  }

  private fun setupHistoryRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.historyRecyclerView.adapter = adapter

    viewModel.searchHistories
      .observe(viewLifecycleOwner) { histories ->
        if (histories.isEmpty()) {
          binding.trendingRecyclerView.visibility = View.VISIBLE
          binding.historyRecyclerView.visibility = View.INVISIBLE
        } else {
          binding.trendingRecyclerView.visibility = View.INVISIBLE
          binding.historyRecyclerView.visibility = View.VISIBLE
          adapter.updateAsync(histories.map { history ->
            SearchHistoryItem(
              history = history
            )
          })
        }
      }
  }

  private fun setupSearchedRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.searchRecyclerView.adapter = adapter

    val upvoteListener = { _: Article -> }
    val downvoteListener = { _: Article -> }
    val communityListener = { _: Article -> }

    viewModel.searchArticles
      .observe(viewLifecycleOwner) { articles ->
        adapter.updateAsync(articles.map { article ->
          ArticleItem(
            article = article,
            imageLoader = imageLoader,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener,
            communityListener = communityListener
          )
        })
      }
  }

}