package com.yuyakaido.android.gaia.community.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.yuyakaido.android.gaia.community.detail.databinding.ActivityCommunityDetailBinding
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.presentation.BaseActivity
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import javax.inject.Inject

class CommunityDetailActivity : BaseActivity() {

  companion object {
    private val COMMUNITY = Community::class.java.simpleName

    fun createIntent(context: Context, community: Community.Summary): Intent {
      return Intent(context, CommunityDetailActivity::class.java)
        .apply { putExtra(COMMUNITY, community) }
    }
  }

  @Inject
  internal lateinit var factory: ViewModelFactory<CommunityDetailViewModel>

  private val viewModel: CommunityDetailViewModel by viewModels { factory }
  private val binding by lazy { ActivityCommunityDetailBinding.inflate(layoutInflater) }

  internal fun getCommunity(): Community.Summary {
    return intent.getParcelableExtra(COMMUNITY) as Community.Summary
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    viewModel.onBind()
  }

}