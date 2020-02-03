package com.yuyakaido.android.gaia.article.list

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.ArticleItem
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class ArticleListFragment : DaggerFragment() {

  companion object {
    fun newInstance(args: ArticleListFragmentArgs): Fragment {
      return ArticleListFragment()
        .apply {
          arguments = args.toBundle()
        }
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<ArticleListViewModel>

  internal val args: ArticleListFragmentArgs by navArgs()

  private val viewModel: ArticleListViewModel by viewModels { factory }

  private lateinit var binding: FragmentArticleListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val parent = requireContext()
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
    setupRecyclerView()
    Timber.d("fragment = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
    viewModel.onBind()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_article_list, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val page = ArticleListPage.from(item.itemId)
    refreshByPage(page = page)
    return super.onOptionsItemSelected(item)
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        appRouter.navigateToArticleDetailActivity(
          controller = findNavController(),
          article = item.article
        )
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .showLastDivider()
        .build()
    )
    binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
      appRouter.navigateToCommunityDetail(
        controller = findNavController(),
        community = article.community
      )
    }

    viewModel.items
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

  private fun refreshByPage(page: ArticleListPage) {
    viewModel.onRefresh(page.source)
  }

}