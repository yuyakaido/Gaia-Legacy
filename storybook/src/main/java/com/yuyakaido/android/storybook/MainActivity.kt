package com.yuyakaido.android.storybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import com.yuyakaido.android.storybook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    val transaction = supportFragmentManager.beginTransaction().apply {
      replace(
        binding.container.id,
        StorybookFragment()
      )
    }
  }
}
