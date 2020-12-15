package com.yuyakaido.android.gaia.user.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import com.yuyakaido.android.gaia.user.databinding.FragmentUserListBinding

class UserListFragment : BaseFragment<UserListViewModel>() {

  companion object {
    private val SOURCE = UserListSource::class.java.simpleName

    fun newInstance(source: UserListSource): Fragment {
      return UserListFragment()
        .apply { arguments = bundleOf(SOURCE to source) }
    }
  }

  override val viewModel: UserListViewModel by viewModels { factory }
  private lateinit var binding: FragmentUserListBinding

  internal fun getUserListSource(): UserListSource {
    return requireNotNull(arguments?.getParcelable(SOURCE))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentUserListBinding.inflate(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    val manager = LinearLayoutManager(requireContext())
    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is UserItem) {
        appNavigator.navigateToUserDetail(
          findNavController(),
          user = item.user
        )
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter

    viewModel.users
      .observe(viewLifecycleOwner) { users ->
        adapter.updateAsync(
          users.map { user ->
            UserItem(user = user)
          }
        )
      }
  }

}