package com.yuyakaido.android.gaia.core.domain.entity

interface VotableType {
  val name: String
  val likes: Boolean?
  fun <T : VotableType> toVoted(likes: Boolean?): T
}