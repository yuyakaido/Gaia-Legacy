package com.yuyakaido.android.gaia

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
import com.yuyakaido.android.gaia.databinding.FragmentSubredditListBinding

class SubredditListFragment : Fragment() {

  companion object {
    private val PAGE = HomePage::class.java.simpleName

    fun newInstance(page: HomePage): Fragment {
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
    val page = arguments?.getSerializable(PAGE) as HomePage
    viewModel.onBind(page = page)
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is SubredditItem) {
        startActivity(SubredditActivity.createIntent(requireContext(), item.subreddit))
      }
    }

    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter

    viewModel
      .subreddits
      .observe(this) { subreddits ->
        adapter.updateAsync(subreddits.map { subreddit -> SubredditItem(subreddit = subreddit) })
      }
  }

}