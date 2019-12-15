package com.yuyakaido.android.gaia.core.domain.extension

import android.content.Context

fun Int.dpTpPx(context: Context): Int {
  return (context.resources.displayMetrics.density * this).toInt()
}