package com.yuyakaido.android.gaia

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class GithubClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addNetworkInterceptor(StethoInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val service = retrofit.create(GithubService::class.java)

    fun search(query: String): Single<List<Repo>> {
        return service.search(query = query)
            .map { response -> response.items.map { item -> item.toRepo() } }
    }

    interface GithubService {
        @GET("search/repositories")
        fun search(@Query("q") query: String): Single<SearchResponse>
    }

}