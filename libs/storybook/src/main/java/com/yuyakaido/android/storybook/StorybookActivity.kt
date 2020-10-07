package com.yuyakaido.android.storybook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.storybook.databinding.ActivityStorybookBinding

class StorybookActivity : AppCompatActivity() {

  companion object {
    private lateinit var storybook: Storybook

    fun createIntent(context: Context, storybook: Storybook): Intent {
      this.storybook = storybook
      return Intent(context, StorybookActivity::class.java)
    }
  }

  private val binding by lazy { ActivityStorybookBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    val database = Room
      .databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "AppDatabase")
      .allowMainThreadQueries()
      .build()
    val historyDao = database.historyDao()

    if (storybook.hasElement()) {
      val history = History(name = storybook.name)
      historyDao.insert(history)
      Log.d("Storybook", "Insert = ${history.name}")
    }

    val lastItem = if (storybook.isFirstSection()) {
      val history = historyDao.getLatest()
      history?.let {
        val item = Storybook.find(storybook, history)
        Log.d("Storybook", "History = ${history.name}")
        Log.d("Storybook", "Item = ${item?.name}")
        item
      }
    } else {
      null
    }

    title = storybook.name
    if (storybook.hasParent()) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    val adapter = GroupAdapter<GroupieViewHolder>()
    adapter.addAll(storybook.toGroupieItems(lastItem))
    adapter.setOnItemClickListener { item, _ ->
      when (item) {
        is SectionItem -> {
          startActivity(createIntent(this, storybook.toNext(item.section)))
        }
      }
    }
    binding.recyclerView.layoutManager = LinearLayoutManager(this)
    binding.recyclerView.adapter = adapter
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> finish()
    }
    return super.onOptionsItemSelected(item)
  }

}