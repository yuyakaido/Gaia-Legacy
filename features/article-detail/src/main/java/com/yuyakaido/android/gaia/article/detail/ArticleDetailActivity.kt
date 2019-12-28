package com.yuyakaido.android.gaia.article.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.yuyakaido.android.gaia.article.detail.databinding.ActivityArticleDetailBinding
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.presentation.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ArticleDetailActivity : DaggerAppCompatActivity() {

  companion object {
    private val ARTICLE = Article::class.java.simpleName

    fun createIntent(context: Context, article: Article): Intent {
      return Intent(context, ArticleDetailActivity::class.java)
        .apply { putExtra(ARTICLE, article) }
    }
  }

  @Inject
  internal lateinit var factory: ViewModelFactory<ArticleDetailViewModel>

  private val viewModel: ArticleDetailViewModel by viewModels { factory }
  private val binding by lazy { ActivityArticleDetailBinding.inflate(layoutInflater) }

  internal fun getArticle(): Article {
    return requireNotNull(intent.getParcelableExtra(ARTICLE))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupDetail()
    viewModel.onBind()
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
  }

}