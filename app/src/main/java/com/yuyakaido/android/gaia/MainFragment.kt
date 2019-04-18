package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var repository: RepoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repository.fetchRepos(query = "Gaia")
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - MainFragment",
                    "hash = ${repository.hashCode()}, size = ${repos.size}"
                )
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.dispose()
        super.onDestroyView()
    }

}