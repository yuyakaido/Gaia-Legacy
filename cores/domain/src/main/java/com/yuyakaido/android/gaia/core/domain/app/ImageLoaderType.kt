package com.yuyakaido.android.gaia.core.domain.app

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

interface ImageLoaderType {
  fun load(uri: Uri, placeholder: Drawable, view: ImageView)
}