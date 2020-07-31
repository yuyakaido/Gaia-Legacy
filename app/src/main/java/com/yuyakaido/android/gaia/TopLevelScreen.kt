package com.yuyakaido.android.gaia

import android.view.View

enum class TopLevelScreen(
  val id: Int
) {
  Article(
    id = R.id.fragment_article_list
  ),
  Search(
    id = R.id.fragment_search
  ),
  Community(
    id = R.id.fragment_community_list
  ),
  Profile(
    id = R.id.fragment_user_detail
  );
  companion object {
    fun from(id: Int): TopLevelScreen {
      return values().first { it.id == id }
    }
    fun visibility(id: Int): Int {
      return values().find { it.id == id }?.let { View.VISIBLE } ?: View.GONE
    }
    fun toSet(): Set<Int> {
      return values().map { it.id }.toSet()
    }
  }
}