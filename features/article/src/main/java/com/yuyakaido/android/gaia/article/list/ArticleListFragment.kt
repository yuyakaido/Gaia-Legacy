package com.yuyakaido.android.gaia.article.list

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yuyakaido.android.gaia.article.R
import com.yuyakaido.android.gaia.article.databinding.FragmentArticleListBinding
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.ArticleItem
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticleListFragment : BaseFragment<ArticleListViewModel>() {

  companion object {
    fun newInstance(args: ArticleListFragmentArgs): Fragment {
      return ArticleListFragment()
        .apply {
          arguments = args.toBundle()
        }
    }
  }

  override val viewModel: ArticleListViewModel by viewModels { factory }
  internal val args: ArticleListFragmentArgs by navArgs()
  private lateinit var binding: FragmentArticleListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val parent = requireParentFragment()
    if (parent is OptionMenuType) {
      Handler().post { setHasOptionsMenu(parent.shouldShowOptionMenu()) }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentArticleListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    setupRecyclerViewWithoutPaging()
    setupRecyclerViewWithPaging()
    Timber.d("fragment = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_article_list, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val page = ArticleListPage.from(item.itemId)
    refreshByPage(page = page)
    return super.onOptionsItemSelected(item)
  }

  private fun setupRecyclerViewWithoutPaging() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        appNavigator.navigateToArticleDetailActivity(
          controller = findNavController(),
          article = item.article
        )
      }
    }

    binding.recyclerViewWithoutPaging.layoutManager = manager
    binding.recyclerViewWithoutPaging.adapter = adapter
    binding.recyclerViewWithoutPaging.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .showLastDivider()
        .build()
    )
    binding.recyclerViewWithoutPaging
      .addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
          val itemCount = manager.itemCount
          val lastPosition = manager.findLastCompletelyVisibleItemPosition()
          if (itemCount != 0 && lastPosition == itemCount - 1) {
            viewModel.onPaginate()
          }
        }
      })

    val upvoteListener = { article: Article -> viewModel.onUpvote(article = article) }
    val downvoteListener = { article: Article -> viewModel.onDownvote(article = article) }
    val communityListener = { article: Article ->
      appNavigator.navigateToCommunityDetail(
        controller = findNavController(),
        community = article.community
      )
    }

    viewModel.itemsWithoutPaging
      .map { items ->
        items.flatMap { item -> item.entities }
      }
      .observe(viewLifecycleOwner) { items ->
        adapter.updateAsync(items.map { item ->
          ArticleItem(
            article = item,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener,
            communityListener = communityListener
          )
        })
      }
  }

  private fun setupRecyclerViewWithPaging() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = PagingGroupAdapter()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        appNavigator.navigateToArticleDetailActivity(
          controller = findNavController(),
          article = item.article
        )
      }
    }

    binding.recyclerViewWithPaging.layoutManager = manager
    binding.recyclerViewWithPaging.adapter = adapter
    binding.recyclerViewWithPaging.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .showLastDivider()
        .build()
    )

    lifecycleScope.launch {
      viewModel.itemsWithPaging
        .collect {
          adapter.submitData(it)
        }
    }
  }

  private fun refreshByPage(page: ArticleListPage) {
    viewModel.onRefresh(page.source)
  }

}