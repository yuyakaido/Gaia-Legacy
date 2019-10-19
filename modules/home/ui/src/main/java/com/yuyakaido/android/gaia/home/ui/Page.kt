package com.yuyakaido.android.gaia.home.ui

enum class Page {
  Repo,
  Profile;

  companion object {
    fun fromPosition(position: Int): Page {
      return values()[position]
    }
  }
}