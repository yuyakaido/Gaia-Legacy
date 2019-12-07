package com.yuyakaido.android.gaia.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.Article
import com.yuyakaido.android.gaia.core.ArticleItem
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
    setupEditText()
    setupTrendingRecyclerView()
    setupSearchedRecyclerView()
    viewModel.onBind()
  }

  private fun setupEditText() {
    binding.editText
      .doOnTextChanged { text, _, _, _ ->
        viewModel.onTextChange(text = text.toString())
      }
  }

  private fun setupTrendingRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.trendingRecyclerView.adapter = adapter

    viewModel
      .trendingArticles
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

    viewModel
      .searchedArticles
      .observe(viewLifecycleOwner) { articles ->
        adapter.updateAsync(articles.map { article ->
          ArticleItem(
            article = article,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener
          )
        })
      }
  }

}