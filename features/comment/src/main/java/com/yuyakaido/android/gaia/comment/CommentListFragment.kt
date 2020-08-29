package com.yuyakaido.android.gaia.comment

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
import com.yuyakaido.android.gaia.comment.databinding.FragmentCommentListBinding
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.extension.dpTpPx
import com.yuyakaido.android.gaia.core.presentation.BaseFragment

class CommentListFragment : BaseFragment<CommentListViewModel>() {

  companion object {
    private val SOURCE = CommentListSource::class.java.simpleName

    fun createIntent(article: Article): Fragment {
      return CommentListFragment()
        .apply {
          arguments = bundleOf(
            SOURCE to CommentListSource.Article(value = article)
          )
        }
    }

    fun createIntent(user: User): Fragment {
      return CommentListFragment()
        .apply {
          arguments = bundleOf(
            SOURCE to CommentListSource.User(value = user)
          )
        }
    }
  }

  override val viewModel: CommentListViewModel by viewModels { factory }
  private lateinit var binding: FragmentCommentListBinding

  internal fun getCommentListSource(): CommentListSource {
    return requireNotNull(requireArguments().getParcelable(SOURCE))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCommentListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addItemDecoration(
      HorizontalDividerItemDecoration.Builder(requireContext())
        .color(Color.TRANSPARENT)
        .size(8.dpTpPx(requireContext()))
        .showLastDivider()
        .build()
    )

    val upvoteListener = { comment: Comment -> viewModel.onUpvote(comment = comment) }
    val downvoteListener = { comment: Comment -> viewModel.onDownvote(comment = comment) }

    viewModel.comments
      .observe(viewLifecycleOwner) { comments ->
        adapter.updateAsync(
          comments.map { comment ->
            CommentItem(
              comment = comment,
              upvoteListener = upvoteListener,
              downvoteListener = downvoteListener
            )
          }
        )
      }
  }

}