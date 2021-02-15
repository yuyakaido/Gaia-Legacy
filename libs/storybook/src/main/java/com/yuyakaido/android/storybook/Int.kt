package com.yuyakaido.android.storybook

import android.content.Context

fun Int.dpToPx(context: Context): Int {
  return (context.resources.displayMetrics.density * this).toInt()
}