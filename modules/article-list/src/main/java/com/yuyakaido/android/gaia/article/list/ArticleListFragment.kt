package com.yuyakaido.android.gaia.article.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration
import com.yuyakaido.android.gaia.article.list.databinding.FragmentArticleListBinding
import com.yuyakaido.android.gaia.core.*
import timber.log.Timber
import javax.inject.Inject

class ArticleListFragment : BaseFragment() {

  companion object {
    private val PAGE = ArticleListPage::class.java.simpleName

    fun newInstance(page: ArticleListPage): Fragment {
      return ArticleListFragment()
        .apply { arguments = bundleOf(PAGE to page) }
    }
  }

  @Inject
  internal lateinit var factory: ViewModelFactory<ArticleListViewModel>

  private val viewModel: ArticleListViewModel by activityViewModels { factory }

  private lateinit var binding: FragmentArticleListBinding

  internal fun getArticleListPage(): ArticleListPage {
    return requireArguments().getSerializable(PAGE) as ArticleListPage
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

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        val app = requireActivity().application as GaiaType
        startActivity(app.newArticleDetailActivity(item.article))
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
        val page = arguments?.getSerializable(PAGE) as ArticleListPage
        val itemCount = manager.itemCount
        val lastPosition = manager.findLastCompletelyVisibleItemPosition()
        if (itemCount != 0 && lastPosition == itemCount - 1) {
          viewModel.onPaginate(page)
        }
      }
    })

    val upvoteListener = { article: Article -> viewModel.onUpvote(article = article) }
    val downvoteListener = { article: Article -> viewModel.onDownvote(article = article) }

    viewModel
      .items
      .map { items ->
        items.flatMap { item -> item.entities }
      }
      .observe(viewLifecycleOwner) { items ->
        adapter.updateAsync(items.map { item ->
          ArticleItem(
            article = item,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener
          )
        })
      }
  }

}