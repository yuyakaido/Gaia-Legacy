package com.yuyakaido.android.gaia.core

import android.content.Context

fun Int.dpTpPx(context: Context): Int {
  return (context.resources.displayMetrics.density * this).toInt()
}