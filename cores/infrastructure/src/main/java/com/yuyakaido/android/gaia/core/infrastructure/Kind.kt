package com.yuyakaido.android.gaia.core.infrastructure

enum class Kind(val id: String) {
  Comment(id = Kind.comment),
  Account(id = Kind.account),
  Article(id = Kind.article),
  Message(id = Kind.message),
  Community(id = Kind.community),
  Award(id = Kind.award),
  More(id = Kind.more);

  companion object {
    const val classDiscriminator = "kind"
    const val comment = "t1"
    const val account = "t2"
    const val article = "t3"
    const val message = "t4"
    const val community = "t5"
    const val award = "t6"
    const val more = "more"
  }
}