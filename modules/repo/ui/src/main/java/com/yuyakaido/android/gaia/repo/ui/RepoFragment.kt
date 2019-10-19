package com.yuyakaido.android.gaia.repo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.core.android.RepoDetailIntentResolverType
import com.yuyakaido.android.gaia.repo.ui.databinding.FragmentRepoBinding
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoFragment : DaggerFragment() {

  companion object {
    fun newInstance(): Fragment {
      return RepoFragment()
    }
  }

  private val disposables = CompositeDisposable()
  private val binding by lazy { FragmentRepoBinding.inflate(layoutInflater) }

  @Inject
  lateinit var viewModel: RepoViewModel

  @Inject
  lateinit var resolver: RepoDetailIntentResolverType

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val adapter = GroupAdapter<ViewHolder>()
    adapter.setOnItemClickListener { item, _ ->
      if (item is RepoItem) {
        startActivity(resolver.getRepoDetailActivityIntent(requireContext(), item.repo))
      }
    }
    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter

    binding.editText.textChanges()
      .skipInitialValue()
      .throttleLast(1, TimeUnit.SECONDS)
      .filter { it.isNotEmpty() }
      .map { it.toString() }
      .subscribeBy { query ->
        viewModel.getRepos(query)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeBy { repos ->
            adapter.clear()
            adapter.addAll(repos.map { RepoItem(it) })
          }
          .addTo(disposables)
      }
      .addTo(disposables)
  }

  override fun onDestroyView() {
    disposables.dispose()
    super.onDestroyView()
  }

}