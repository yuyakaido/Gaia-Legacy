package com.yuyakaido.android.gaia.article.detail

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.article.detail.databinding.ItemCommentBinding
import com.yuyakaido.android.gaia.core.Comment

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