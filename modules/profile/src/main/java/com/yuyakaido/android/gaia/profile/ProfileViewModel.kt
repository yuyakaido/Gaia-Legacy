package com.yuyakaido.android.gaia.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.Me
import com.yuyakaido.android.gaia.core.MeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
  application: Application
) : AndroidViewModel(
  application
) {

  private val service = (getApplication<GaiaType>()).redditService

  val me = MutableLiveData<Me>()

  fun onBind() {
    service
      .me()
      .enqueue(object : Callback<MeResponse> {
        override fun onResponse(call: Call<MeResponse>, response: Response<MeResponse>) {
          response.body()?.let { body ->
            me.postValue(body.toEntity())
          }
        }
        override fun onFailure(call: Call<MeResponse>, t: Throwable) {
          Log.d("Gaia", t.toString())
        }
      })
  }

}