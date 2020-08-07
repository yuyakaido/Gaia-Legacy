package com.yuyakaido.android.gaia.article.list

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.AdapterListUpdateCallback
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class PagingGroupAdapter : GroupAdapter<GroupieViewHolder>() {

  private val differ = AsyncPagingDataDiffer(
    AnyDiffCallback(),
    AdapterListUpdateCallback(this)
  )

  override fun getItemCount(): Int {
    return differ.itemCount
  }

  override fun getItem(position: Int): Item<*> {
    return differ.getItem(position) as Item<*>
  }

  suspend fun submitData(data: PagingData<Item<*>>) {
    differ.submitData(data)
  }

}