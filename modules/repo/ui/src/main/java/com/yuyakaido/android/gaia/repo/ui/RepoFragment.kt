package com.yuyakaido.android.gaia.repo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.yuyakaido.android.gaia.core.android.BarIntentResolverType
import com.yuyakaido.android.gaia.core.java.AppStore
import com.yuyakaido.android.gaia.core.java.Session
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

    @Inject
    lateinit var appStore: AppStore

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var viewModel: RepoViewModel

    @Inject
    lateinit var resolver: BarIntentResolverType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(
            "Gaia - RepoFragment@${hashCode()}",
            "appStore = ${appStore.hashCode()}, session = ${session.hashCode()}"
        )

        val adapter = GroupAdapter<ViewHolder>()
        adapter.setOnItemClickListener { item, _ ->
            if (item is RepoItem) {
                startActivity(resolver.getBarActivityIntent(requireContext(), item.repo))
            }
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val editText = view.findViewById<EditText>(R.id.edit_text)
        editText.textChanges()
            .skipInitialValue()
            .throttleLast(1, TimeUnit.SECONDS)
            .map { it.toString() }
            .subscribeBy { query ->
                viewModel.getRepos(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { repos ->
                        Log.d(
                            "Gaia - RepoFragment@${hashCode()}",
                            "vm = ${viewModel.hashCode()}, repos = ${repos.size}"
                        )

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