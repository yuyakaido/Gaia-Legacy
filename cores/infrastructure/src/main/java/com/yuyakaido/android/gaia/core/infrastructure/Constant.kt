package com.yuyakaido.android.gaia.core.infrastructure

object Constant {

  const val OAUTH_SCHEME = "https"
  const val OAUTH_AUTHORITY = "www.reddit.com"
  const val OAUTH_PATH = "api/v1/authorize.compact"
  const val OAUTH_CLIENT_ID = "tDtKJlrx9OTNiA"
  const val OAUTH_RESPONSE_TYPE = "code"
  const val OAUTH_REDIRECT_URI = "com.yuyakaido.android.gaia://complete_authorization"
  val OAUTH_SCOPES = listOf(
    "creddits",
    "modcontributors",
    "modmail",
    "modconfig",
    "subscribe",
    "structuredstyles",
    "vote",
    "wikiedit",
    "mysubreddits",
    "submit",
    "modlog",
    "modposts",
    "modflair",
    "save",
    "modothers",
    "read",
    "privatemessages",
    "report",
    "identity",
    "livemanage",
    "account",
    "modtraffic",
    "wikiread",
    "edit",
    "modwiki",
    "modself",
    "history",
    "flair"
  )

}