package com.yuyakaido.android.gaia.core.infrastructure.remote.response

enum class Kind(val id: String) {
  Comment(id = "t1"),
  Account(id = "t2"),
  Article(id = "t3"),
  Message(id = "t4"),
  Community(id = "t5"),
  Award(id = "t6"),
  More(id = "more")
}