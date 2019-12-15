package com.yuyakaido.android.gaia.profile

import android.graphics.Color
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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.ArticleItem
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.core.domain.value.VoteListPage
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
            (ME to me),
            (PAGE to page)
          )
        }
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<VoteListViewModel>

  private val viewModel: VoteListViewModel by viewModels { factory }

  private lateinit var binding: FragmentVoteListBinding

  internal fun getMe(): Me {
    return requireArguments().getParcelable<Me>(ME) as Me
  }

  internal fun getVoteListPage(): VoteListPage {
    return requireArguments().getSerializable(PAGE) as VoteListPage
  }

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
    viewModel.onBind()
  }

  private fun setupRecyclerView() {
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is ArticleItem) {
        startActivity(appRouter.newArticleDetailActivity(item.article))
      }
    }

    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .showLastDivider()
        .build()
    )

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