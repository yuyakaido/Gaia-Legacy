package com.yuyakaido.android.storybook

import android.view.View
import com.xwray.groupie.Group

class Storybook(
  val name: String,
  val items: List<Item>,
  private val depth: Int = 0
) {
  companion object {
    fun find(storybook: Storybook, history: History): Item? {
      return if (storybook.isLastSection()) {
        storybook.items
          .find { it.name == history.name }
      } else {
        storybook.items
          .map { find(storybook.toNext(it), history) }
          .find { it?.name == history.name }
      }
    }
  }
  fun toGroupieItems(lastItem: Item?): List<Group> {
    val groups = items.map { it.toGroupieItem() }
    return lastItem?.let {
      listOf(it.toGroupieItem()).plus(groups)
    } ?: groups
  }
  fun toNext(item: Item): Storybook {
    return Storybook(
      name = item.name,
      items = item.items(),
      depth = depth.inc()
    )
  }
  fun hasParent(): Boolean {
    return depth > 0
  }
  fun isFirstSection(): Boolean {
    return !hasParent()
  }
  fun isLastSection(): Boolean {
    return items.any { it.isLastSection() }
  }
  fun hasElement(): Boolean {
    return items.any { it is Item.Element }
  }
}

sealed class Item {
  abstract val name: String
  abstract fun items(): List<Item>
  abstract fun toGroupieItem(): Group
  abstract fun isLastSection(): Boolean

  class Section(
    override val name: String,
    val items: List<Item>
  ) : Item() {
    override fun items(): List<Item> {
      return items
    }
    override fun toGroupieItem(): Group {
      return SectionItem(this)
    }
    override fun isLastSection(): Boolean {
      return items.any { it is Element }
    }
  }

  sealed class Element : Item() {
    abstract class Platform(
      override val name: String
    ) : Element() {
      abstract fun view(): View
      override fun items(): List<Item> {
        return listOf(this)
      }
      override fun toGroupieItem(): Group {
        return PlatformElementItem(this)
      }
      override fun isLastSection(): Boolean {
        return false
      }
    }
    abstract class Groupie(
      override val name: String
    ) : Element() {
      abstract fun group(): Group
      override fun items(): List<Item> {
        return listOf(this)
      }
      override fun toGroupieItem(): Group {
        return GroupieElementItem(this)
      }
      override fun isLastSection(): Boolean {
        return false
      }
    }
  }
}
