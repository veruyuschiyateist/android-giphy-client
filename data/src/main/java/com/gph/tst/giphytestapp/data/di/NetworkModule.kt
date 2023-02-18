package com.gph.tst.giphytestapp.data.di

import androidx.room.PrimaryKey
import com.gph.tst.giphytestapp.data.network.api.GiphyApi
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
    fun provideGiphyApi(retrofit: Retrofit): GiphyApi = retrofit.create(GiphyApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { putQueryApiKey(it) }
            .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

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

