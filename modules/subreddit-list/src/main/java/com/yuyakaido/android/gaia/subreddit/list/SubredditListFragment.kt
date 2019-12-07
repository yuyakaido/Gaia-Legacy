package com.yuyakaido.android.gaia.subreddit.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.BaseFragment
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.Subreddit
import com.yuyakaido.android.gaia.core.SubredditItem
import com.yuyakaido.android.gaia.subreddit.list.databinding.FragmentSubredditListBinding
import timber.log.Timber
import javax.inject.Inject

class SubredditListFragment : BaseFragment() {

  companion object {
    private val PAGE = SubredditListPage::class.java.simpleName

    fun newInstance(page: SubredditListPage): Fragment {
      return SubredditListFragment()
        .apply { arguments = bundleOf(PAGE to page) }
    }
  }

  @Inject
  internal lateinit var viewModel: SubredditListViewModel

  private lateinit var binding: FragmentSubredditListBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentSubredditListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    val page = arguments?.getSerializable(PAGE) as SubredditListPage
    Timber.d("fragment = ${hashCode()}")
    Timber.d("viewmodel = ${viewModel.hashCode()}")
    viewModel.onBind(page = page)
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SubredditItem) {
        val app = requireActivity().application as GaiaType
        startActivity(app.newSubredditDetailActivity(item.subreddit))
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val page = arguments?.getSerializable(PAGE) as SubredditListPage
        val itemCount = manager.itemCount
        val lastPosition = manager.findLastCompletelyVisibleItemPosition()
        if (itemCount != 0 && lastPosition == itemCount - 1) {
          viewModel.onPaginate(page)
        }
      }
    })

    val upvoteListener = { subreddit: Subreddit -> viewModel.onUpvote(subreddit = subreddit) }
    val downvoteListener = { subreddit: Subreddit -> viewModel.onDownvote(subreddit = subreddit) }

    viewModel
      .items
      .map { items ->
        items.flatMap { item -> item.entities }
      }
      .observe(viewLifecycleOwner) { items ->
        adapter.updateAsync(items.map { item ->
          SubredditItem(
            subreddit = item,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener
          )
        })
      }
  }

}