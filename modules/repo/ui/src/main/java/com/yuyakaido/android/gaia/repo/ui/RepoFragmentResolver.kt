package com.yuyakaido.android.gaia.repo.ui

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.android.RepoFragmentResolverType

class RepoFragmentResolver : RepoFragmentResolverType {

    override fun getRepoFragment(): Fragment {
        return RepoFragment.newInstance()
    }

}