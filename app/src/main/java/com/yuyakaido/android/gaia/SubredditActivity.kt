package com.yuyakaido.android.gaia

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yuyakaido.android.gaia.databinding.ActivitySubredditBinding

class SubredditActivity : AppCompatActivity() {

  companion object {
    private val SUBREDDIT = Subreddit::class.java.simpleName

    fun createIntent(context: Context, subreddit: Subreddit): Intent {
      return Intent(context, SubredditActivity::class.java)
        .apply { putExtra(SUBREDDIT, subreddit) }
    }
  }

  private val subreddit by lazy { intent.getParcelableExtra(SUBREDDIT) as Subreddit }
  private val binding by lazy { ActivitySubredditBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    binding.title.text = subreddit.title
    Glide
      .with(binding.root.context)
      .load(subreddit.thumbnail)
      .placeholder(ColorDrawable(Color.LTGRAY))
      .into(binding.thumbnail)
  }

}