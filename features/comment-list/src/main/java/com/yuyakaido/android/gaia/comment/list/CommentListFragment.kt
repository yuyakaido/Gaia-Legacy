package com.yuyakaido.android.gaia.comment.list

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
import com.yuyakaido.android.gaia.comment.list.databinding.FragmentCommentListBinding
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CommentListFragment : DaggerFragment() {

  companion object {
    private val USER = User::class.java.simpleName

    fun createIntent(user: User): Fragment {
      return CommentListFragment()
        .apply { arguments = bundleOf(USER to user) }
    }
  }

  @Inject
  internal lateinit var factory: ViewModelFactory<CommentListViewModel>

  private val viewModel: CommentListViewModel by viewModels { factory }

  private lateinit var binding: FragmentCommentListBinding

  internal fun getUser(): User {
    return requireNotNull(requireArguments().getParcelable(USER))
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
    viewModel.onBind()
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter

    viewModel
      .comments
      .observe(viewLifecycleOwner) { comments ->
        val items = comments.map { comment -> CommentItem(comment = comment) }
        adapter.updateAsync(items)
      }
  }

}