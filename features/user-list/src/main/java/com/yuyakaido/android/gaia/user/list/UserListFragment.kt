package com.yuyakaido.android.gaia.user.list

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
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.value.UserListSource
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import com.yuyakaido.android.gaia.user.list.databinding.FragmentUserListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UserListFragment : DaggerFragment() {

  companion object {
    private val SOURCE = UserListSource::class.java.simpleName

    fun newInstance(source: UserListSource): Fragment {
      return UserListFragment()
        .apply { arguments = bundleOf(SOURCE to source) }
    }
  }

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var factory: ViewModelFactory<UserListViewModel>

  private val viewModel: UserListViewModel by viewModels { factory }

  private lateinit var binding: FragmentUserListBinding

  internal fun getUserListSource(): UserListSource {
    return requireNotNull(arguments?.getParcelable(SOURCE))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentUserListBinding.inflate(inflater)
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
    adapter.setOnItemClickListener { item, _ ->
      if (item is UserItem) {
        startActivity(appRouter.newUserDetailActivity(user = item.user))
      }
    }

    binding.recyclerView.layoutManager = manager
    binding.recyclerView.adapter = adapter

    viewModel
      .users
      .observe(viewLifecycleOwner) { users ->
        adapter.updateAsync(users.map { user -> UserItem(user = user) })
      }
  }

}