package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.domain.GetRepoUseCase
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    companion object {
        fun newInstance(): Fragment {
            return MainFragment()
        }
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var usecase: GetRepoUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usecase.getRepos(query = "Gaia")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { repos ->
                Log.d(
                    "Gaia - MainFragment",
                    "hash = ${usecase.hashCode()}, size = ${repos.size}"
                )
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.dispose()
        super.onDestroyView()
    }

}