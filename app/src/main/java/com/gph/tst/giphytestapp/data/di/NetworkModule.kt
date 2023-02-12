package com.gph.tst.giphytestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/gifs/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor { putQueryApiKey(it) }
            .build()

    private fun putQueryApiKey(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .url(
                chain.request().url.newBuilder()
                    .addQueryParameter("api_key", "wkLNlaoLTA6bWqeYC5lCtVovSRyDMFTZ")
                    .build()
            )
            .build()
    )
}

