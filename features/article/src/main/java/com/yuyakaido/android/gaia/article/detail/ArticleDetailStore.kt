package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import android.widget.Toast
import com.yuyakaido.android.reduxkit.StoreType

class ArticleDetailStore(
  application: Application,
  initialState: ArticleDetailState
) : StoreType<ArticleDetailState>(
  initialState = initialState,
  errorHandler = { e -> Toast.makeText(application, e.toString(), Toast.LENGTH_SHORT).show() }
)