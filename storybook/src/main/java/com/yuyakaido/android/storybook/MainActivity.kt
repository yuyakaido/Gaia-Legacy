package com.yuyakaido.android.storybook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yuyakaido.android.gaia.core.presentation.OptionMenuType
import com.yuyakaido.android.storybook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OptionMenuType {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    val router: StorybookRouterType = StorybookRouter(application)

    when (BuildConfig.FLAVOR) {
      "articleList" -> {
        supportFragmentManager.beginTransaction().apply {
          replace(
            binding.container.id,
            router.newInstance()
          )
          commitNow()
        }
      }
      "articleDetail" -> {
        startActivity(
          router.createIntent(this)
        )
      }
    }
  }


  // TODO: 実装考える
  override fun shouldShowOptionMenu(): Boolean {
    return true
  }
}
