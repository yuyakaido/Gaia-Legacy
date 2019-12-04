package com.yuyakaido.android.gaia.subreddit.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.Subreddit
import com.yuyakaido.android.gaia.core.SubredditItem
import com.yuyakaido.android.gaia.subreddit.list.databinding.FragmentSubredditListBinding
import timber.log.Timber

class SubredditListFragment : Fragment() {

  companion object {
    private val PAGE = SubredditListPage::class.java.simpleName

    fun newInstance(page: SubredditListPage): Fragment {
      return SubredditListFragment()
        .apply { arguments = bundleOf(PAGE to page) }
    }
  }

  private val viewModel by viewModels<SubredditListViewModel>()
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
    Timber.d("Gaia - SubredditListFragment: fragment = ${hashCode()}")
    Timber.d("Gaia - SubredditListFragment: viewmodel = ${viewModel.hashCode()}")
    viewModel.onBind(page = page)
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SubredditItem) {
        val app = requireActivity().application as GaiaType
        startActivity(app.newSubredditDetailActivity(item.subreddit))
      }
    }

    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter

    val upvoteListener = { subreddit: Subreddit -> viewModel.onUpvote(subreddit = subreddit) }
    val downvoteListener = { subreddit: Subreddit -> viewModel.onDownvote(subreddit = subreddit) }

    viewModel
      .subreddits
      .observe(viewLifecycleOwner) { subreddits ->
        adapter.updateAsync(subreddits.map { subreddit ->
          SubredditItem(
            subreddit = subreddit,
            upvoteListener = upvoteListener,
            downvoteListener = downvoteListener
          )
        })
      }
  }

}