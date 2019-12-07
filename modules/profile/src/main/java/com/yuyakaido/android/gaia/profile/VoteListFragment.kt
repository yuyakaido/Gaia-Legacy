package com.yuyakaido.android.gaia.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.profile.databinding.FragmentVoteListBinding
import javax.inject.Inject

class VoteListFragment : BaseFragment() {

  companion object {
    private val ME = Me::class.java.simpleName
    private val PAGE = VoteListPage::class.java.simpleName

    fun newInstance(me: Me, page: VoteListPage): Fragment {
      return VoteListFragment()
        .apply {
          arguments = bundleOf(
            (PAGE to page),
            (ME to me)
          )
        }
    }
  }

  @Inject
  internal lateinit var viewModel: VoteListViewModel

  private lateinit var binding: FragmentVoteListBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentVoteListBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecyclerView()
    val bundle = requireArguments()
    val me = bundle.getParcelable<Me>(ME) as Me
    val page = bundle.getSerializable(PAGE) as VoteListPage
    viewModel.onBind(me = me, page = page)
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        val app = requireActivity().application as GaiaType
        startActivity(app.newArticleDetailActivity(item.article))
      }
    }

    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter

    val upvoteListener = { _: Article -> Unit }
    val downvoteListener = { _: Article -> Unit }

    viewModel.articles
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