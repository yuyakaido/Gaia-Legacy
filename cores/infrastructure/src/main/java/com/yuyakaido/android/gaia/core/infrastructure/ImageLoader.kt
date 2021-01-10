package com.yuyakaido.android.gaia.core.infrastructure

import android.app.Application
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType

class ImageLoader(
  private val application: Application
) : ImageLoaderType {

  override fun load(
    uri: Uri,
    placeholder: Drawable,
    view: ImageView
  ) {
    Glide.with(application)
      .load(uri)
      .placeholder(placeholder)
      .into(view)
  }

}