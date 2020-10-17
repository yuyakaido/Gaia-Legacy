package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.BasicAuthInterceptor
import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import com.yuyakaido.android.gaia.core.infrastructure.OkHttpForPublic
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class SignedOutNetworkModule : MainNetworkModule() {

  @SignedOutScope
  @OkHttpForPublic
  @Provides
  fun provideOkHttpClientForPublic(): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(BasicAuthInterceptor())
      .build()
  }

  @SignedOutScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(
    @OkHttpForPublic client: OkHttpClient
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(MoshiConverterFactory.create(createBaseMoshi()))
      .build()
  }

}