package com.yuyakaido.android.gaia.foo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.android.BarIntentResolverType
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.Session
import com.yuyakaido.android.gaia.ui.R
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FooFragment : DaggerFragment() {

    companion object {
        fun newInstance(): Fragment {
            return FooFragment()
        }
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var appStore: AppStore

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var viewModel: FooViewModel

    @Inject
    lateinit var resolver: BarIntentResolverType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(
            "Gaia - FooFragment@${hashCode()}",
            "appStore = ${appStore.hashCode()}, session = ${session.hashCode()}"
        )

        viewModel.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - FooFragment@${hashCode()}",
                    "vm = ${viewModel.hashCode()}, repos = ${repos.size}"
                )

                val adapter = GroupAdapter<ViewHolder>()
                adapter.addAll(repos.map { RepoItem(it) })
                adapter.setOnItemClickListener { item, _ ->
                    if (item is RepoItem) {
                        startActivity(resolver.getBarActivityIntent(requireContext(), item.repo))
                    }
                }
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.dispose()
        super.onDestroyView()
    }

    fun getQuery(): String {
        return "Android"
    }

}