package dummy

import com.yuyakaido.android.gaia.core.domain.value.TrendingArticle

object DummyTrendingArticle {

  fun createList(size: Int = 10): List<TrendingArticle> {
    return (0 until size).map { index ->
      TrendingArticle(
        name = index.toString()
      )
    }
  }
}