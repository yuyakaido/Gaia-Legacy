package com.yuyakaido.android.gaia.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.yuyakaido.android.gaia.Gaia
import com.yuyakaido.android.gaia.core.java.Environment
import com.yuyakaido.android.gaia.core.java.GithubRetrofit
import com.yuyakaido.android.gaia.profile.infra.GitHubInterceptor
import com.yuyakaido.android.gaia.profile.infra.type.CustomType
import com.yuyakaido.android.gaia.repo.infra.GithubClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI

@Module
class ClientModule {

    @Provides
    fun provideOkHttpClient(gaia: Gaia): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(GitHubInterceptor(gaia))
            .build()
    }

    @GithubRetrofit
    @Provides
    fun provideGithubRetrofit(
        env: Environment,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(env.githubApiEndpoint)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGithubService(
        @GithubRetrofit retrofit: Retrofit
    ): GithubClient.GithubService {
        return retrofit.create(GithubClient.GithubService::class.java)
    }

    @Provides
    fun provideApolloClient(
        env: Environment,
        client: OkHttpClient
    ): ApolloClient {
        val uriAdapter = object : CustomTypeAdapter<URI> {
            override fun decode(value: CustomTypeValue<*>): URI {
                return URI(value.value as String)
            }
            override fun encode(value: URI): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(value.toString())
            }
        }
        return ApolloClient.builder()
            .okHttpClient(client)
            .serverUrl(env.githubGraphQlEndpoint)
            .addCustomTypeAdapter(CustomType.URI, uriAdapter)
            .build()
    }

}