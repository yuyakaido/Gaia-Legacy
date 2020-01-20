package com.yuyakaido.android.storybook.article.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.list.R

class StorybookFragment : Fragment() {

  companion object {
    fun newInstance(): Fragment {
      return StorybookFragment()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_article_list, container, false)
  }

}
