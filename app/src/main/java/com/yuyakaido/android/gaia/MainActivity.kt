package com.yuyakaido.android.gaia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.yuyakaido.android.gaia.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

  private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    val retrofit = Retrofit.Builder()
      .baseUrl("https://www.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
    val service = retrofit.create(RedditService::class.java)

    service.search()
      .enqueue(object : Callback<SearchResult> {
        override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
          response.body()?.let { body ->
            val adapter = GroupAdapter<GroupieViewHolder>()
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerView.adapter = adapter
            adapter.updateAsync(body.toEntities().map { entity -> SubredditItem(subreddit = entity) })
          }
        }
        override fun onFailure(call: Call<SearchResult>, t: Throwable) {
          Log.d("Gaia", t.toString())
        }
      })
  }

}
