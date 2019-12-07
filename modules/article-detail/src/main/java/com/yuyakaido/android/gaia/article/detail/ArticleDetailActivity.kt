package com.yuyakaido.android.gaia.article.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.article.detail.databinding.ActivityArticleDetailBinding
import com.yuyakaido.android.gaia.core.Subreddit

class ArticleDetailActivity : AppCompatActivity() {

  companion object {
    private val SUBREDDIT = Subreddit::class.java.simpleName

    fun createIntent(context: Context, subreddit: Subreddit): Intent {
      return Intent(context, ArticleDetailActivity::class.java)
        .apply { putExtra(SUBREDDIT, subreddit) }
    }
  }

  private val viewModel by viewModels<ArticleDetailViewModel>()
  private val binding by lazy { ActivityArticleDetailBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    val subreddit = intent.getParcelableExtra(SUBREDDIT) as Subreddit
    setupDetail()
    viewModel.onBind(subreddit)
  }

  private fun setupDetail() {
    viewModel.title
      .observe(this) { title ->
        binding.title.text = title
      }
    viewModel.thumbnail
      .observe(this) { thumbnail ->
        Glide.with(binding.root.context)
          .load(thumbnail)
          .placeholder(ColorDrawable(Color.LTGRAY))
          .into(binding.thumbnail)
      }
    viewModel.comments
      .observe(this) { comments ->
        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.comments.layoutManager = LinearLayoutManager(this)
        binding.comments.adapter = adapter
        adapter.updateAsync(comments.map { comment -> CommentItem(comment = comment) })
      }
  }

}