package com.yuyakaido.android.gaia.subreddit.detail

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.core.Comment
import com.yuyakaido.android.gaia.subreddit.detail.databinding.ItemCommentBinding

class CommentItem(
  private val comment: Comment
) : BindableItem<ItemCommentBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_comment
  }

  override fun bind(binding: ItemCommentBinding, position: Int) {
    binding.body.text = comment.body
  }

}