package com.yuyakaido.android.gaia.comment.list

import com.xwray.groupie.databinding.BindableItem
import com.yuyakaido.android.gaia.comment.list.databinding.ItemCommentBinding
import com.yuyakaido.android.gaia.core.domain.entity.Comment

class CommentItem(
  private val comment: Comment
) : BindableItem<ItemCommentBinding>() {

  override fun getLayout(): Int {
    return R.layout.item_comment
  }

  override fun bind(binding: ItemCommentBinding, position: Int) {
    binding.author.text = comment.author
    binding.time.text = comment.created.toString()
    binding.body.text = comment.body
  }

}