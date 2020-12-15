package com.yuyakaido.android.gaia.comment

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import com.yuyakaido.android.gaia.comment.databinding.ItemCommentBinding
import com.yuyakaido.android.gaia.core.domain.entity.Comment

class CommentItem(
  val comment: Comment,
  private val upvoteListener: (Comment) -> Unit,
  private val downvoteListener: (Comment) -> Unit
  ) : BindableItem<ItemCommentBinding>() {

  override fun initializeViewBinding(view: View): ItemCommentBinding {
    return ItemCommentBinding.bind(view)
  }

  override fun isSameAs(other: Item<*>): Boolean {
    return if (other is CommentItem) {
      other.comment.id == comment.id
    } else {
      false
    }
  }

  override fun hasSameContentAs(other: Item<*>): Boolean {
    return if (other is CommentItem) {
      other.comment == comment
    } else {
      false
    }
  }

  override fun getLayout(): Int {
    return R.layout.item_comment
  }

  override fun bind(binding: ItemCommentBinding, position: Int) {
    binding.author.text = comment.author
    binding.time.text = comment.created.toString()
    binding.body.text = comment.body
    binding.upvote.setOnClickListener { upvoteListener.invoke(comment) }
    binding.downvote.setOnClickListener { downvoteListener.invoke(comment) }
    when (comment.likes) {
      null -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      true -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_active)
        binding.downvote.setImageResource(R.drawable.ic_downvote_inactive)
      }
      false -> {
        binding.upvote.setImageResource(R.drawable.ic_upvote_inactive)
        binding.downvote.setImageResource(R.drawable.ic_downvote_active)
      }
    }
  }

}